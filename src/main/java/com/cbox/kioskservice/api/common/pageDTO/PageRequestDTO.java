package com.cbox.kioskservice.api.common.pageDTO;

import java.util.Locale.Category;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PageRequestDTO {
    private int page = 1;
    private int size = 10;
    private String type, keyword;
    private Category category;

    public PageRequestDTO() {
        this(1, 10);
    }

    public PageRequestDTO(int page, int size) {
        this(page, size, null, null);
    }

    public PageRequestDTO(int page, int size, Category category){
        this.page = page <= 0 ? 1 : page;
        this.size = size < 0 || size >= 100 ? 10 : size;
        this.category = category;
    }

    public PageRequestDTO(int page, int size, String type, String keyword) {
        this.page = page <= 0 ? 1 : page;
        this.size = size < 0 || size >= 100 ? 10 : size;
        this.type = type;
        this.keyword = keyword;
    }

    public PageRequestDTO(int page, int size, Category category, String type, String keyword) {
        this.page = page <= 0 ? 1 : page;
        this.size = size < 0 || size >= 100 ? 10 : size;
        this.category = category;
        this.type = type;
        this.keyword = keyword;
    }
    
}
