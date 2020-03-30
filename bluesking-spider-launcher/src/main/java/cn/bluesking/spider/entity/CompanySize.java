package cn.bluesking.spider.entity;

/**
 * 公司规模枚举类。
 * 
 * @author 随心 2020年3月24日
 */
public enum CompanySize {

    LESS_THAN_15(1, 15), 
    BETWEEN_15_50(15, 50), 
    BETWEEN_50_150(50, 150), 
    BETWEEN_150_500(150, 500),
    BETWEEN_500_2000(500, 2000), 
    MORE_THAN_2000(2000, Integer.MAX_VALUE),
    UNKNOWN(Integer.MAX_VALUE, Integer.MAX_VALUE);

    /** 公司最少人数（包含） */
    private int min;
    /** 公司最多人数（不包含） */
    private int max;

    private CompanySize(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public static CompanySize valueOf(int min) {
        CompanySize[] sizes = values();
        for (int i = 0; i < sizes.length; i++) {
            if (sizes[i].min <= min) {
                return sizes[i];
            }
        }
        return UNKNOWN;
    }

}
