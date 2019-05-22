package com.mint.delivery.interfaces;

import com.mint.delivery.dto.Item;

public interface itemInterface {
    public void onItemSelected(Item data);
    public void onItemRemove(Integer data);
}
