package com.example.fee_management_new.Api;

public class Meta {
    public int totalItems;
    public int itemCount;
    public int itemsPerPage;
    public int totalPages;
    public int currentPage;

    public int getTotalItems() {
        return totalItems;
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
