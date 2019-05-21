package tv.mixiong.web.util;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 */
@Data
public class PageBean<T> {

    private long recordsTotal;   //总数
    private long recordsFiltered;
    private List<T> data;

    public PageBean(PageInfo page) {
        this.recordsTotal = page.getTotal();
        this.recordsFiltered = page.getTotal();
        this.data = page.getList();
    }

    public PageBean(long total, List<T> data) {
        this.recordsTotal = total;
        this.recordsFiltered = total;
        this.data = data;
    }
}
