package cn.bluesking.spider.service;

import java.util.Optional;
import java.util.function.Supplier;

import org.apache.ibatis.session.SqlSession;

import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.dao.AddressMapper;
import cn.bluesking.spider.dao.CompanyMapper;
import cn.bluesking.spider.dao.JobMapper;
import cn.bluesking.spider.entity.Address;
import cn.bluesking.spider.entity.Company;
import cn.bluesking.spider.entity.Job;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 持久化服务类工厂。
 * 
 * @author 随心
 * 2020年3月30日
 */
@Slf4j
public enum PersistenceServiceFactory implements PersistenceService {

    SINGLETON;

    /** 为每一个线程维护一份 SqlSession */
    private ThreadLocal<SqlSession> SESSION_HOLDER = new ThreadLocal<>();

    @Override
    public boolean save(@NonNull Job job) {
        return transaction(session -> {
            session.getMapper(JobMapper.class).insert(job);
            if (job.getAddress() != null) {
                session.getMapper(AddressMapper.class).insert(job.getAddress());
            }
            return true;
        }, () -> Boolean.FALSE).booleanValue();
    }

    @Override
    public boolean save(@NonNull Address address) {
        return transaction(session -> {
            return session.getMapper(AddressMapper.class).insert(address) == 1;
        }, () -> Boolean.FALSE).booleanValue();
    }

    @Override
    public boolean save(@NonNull Company company) {
        return transaction(session -> {
            session.getMapper(CompanyMapper.class).insert(company);
            if (company.getAddresses() != null && company.getAddresses().length > 0) {
                AddressMapper addressMapper = session.getMapper(AddressMapper.class);
                addressMapper.insertCompanyToAddress(company);
                addressMapper.insert(company.getAddresses());
            }
            return true;
        }, () -> Boolean.FALSE).booleanValue();
    }

    @Override
    public Job findJobById(@NonNull String id) {
        return transaction(session -> {
            return session.getMapper(JobMapper.class).selectById(id);
        }, () -> null);
    }

    @Override
    public Address findAddressById(String id) {
        return transaction(session -> {
            return session.getMapper(AddressMapper.class).selectById(id);
        }, () -> null);
    }

    @Override
    public Company findCompanyById(@NonNull String id) {
        return transaction(session -> {
            return session.getMapper(CompanyMapper.class).selectById(id);
        }, () -> null);
    }

    @Override
    public boolean deleteJobById(@NonNull String id) {
        return transaction(session -> {
            return session.getMapper(JobMapper.class).deleteById(id) == 1;
        }, () -> false);
    }

    @Override
    public boolean deleteAddressById(@NonNull String id) {
        return transaction(session -> {
            return session.getMapper(AddressMapper.class).deleteById(id) == 1;
        }, () -> false);
    }

    @Override
    public boolean deleteCompanyById(@NonNull String id) {
        return transaction(session -> {
            return session.getMapper(CompanyMapper.class).deleteById(id) == 1;
        }, () -> false);
    }

    private <R> R transaction(MapperOperation<R> operation, @NonNull Supplier<R> exceptionReturn) {
        try {
            return transaction(operation);
        } catch (Exception e) {
            return exceptionReturn.get();
        }
    }

    private <R> R transaction(@NonNull MapperOperation<R> operation) throws Exception {
        SqlSession session = null;
        try {
            session = getSession();
            R result = operation.operate(session);
            session.commit();
            return result;
        } catch (Exception e) {
            LOGGER.error("持久化操作失败，即将回滚事务...", e);
            Optional.ofNullable(session).ifPresent(SqlSession::rollback);
            throw e;
        }
    }

    private SqlSession getSession() {
        if (SESSION_HOLDER.get() == null) {
            SESSION_HOLDER.set(MybatisHelper.getSessionFactory().openSession());
        }
        return SESSION_HOLDER.get();
    }

    private static interface MapperOperation<R> {
        R operate(SqlSession session) throws Exception;
    }

}
