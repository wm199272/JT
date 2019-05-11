package com.jt.service;

import java.util.List;

import com.jt.pojo.Cart;

public interface CartService {

	List<Cart> findCartListByUserId(Long userId);

	void updateCartNum(Cart cart);

	void saveCart(Cart cart);

	void delete(Cart cart);

}
