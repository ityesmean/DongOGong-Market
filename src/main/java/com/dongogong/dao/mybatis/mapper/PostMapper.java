package com.dongogong.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.dongogong.domain.Post;

@Mapper
public interface PostMapper {
	// 전체상품 가져오기
	List<Post> getPostList();
	
	//카테고리별 상품 출력
	 List<Post> getCategory(String borderType);
      
	
	// 내 상품 가져오기
	 List<Post> getPost(String registerId); 
	
	
	// 상품 눌렀을때 정보보기
	Post selectPost(int post_idx);
		
	// 상품 등록
	void registerPost(Post post) throws DataAccessException;
	
	void postUpdate(Post post) throws DataAccessException;
	
	void postDelete(int postIdx) throws DataAccessException;
	
	void transactionDelete(int postIdx) throws DataAccessException;
	void relationDelete(int postIdx) throws DataAccessException;
	
	void chatDelete(int postIdx) throws DataAccessException;
	
	// 전체 상품 가져오기
	Post getPostListSize();
	
	Post getPostIdx(int postIdx) throws DataAccessException;
	
    // 장바구니 추가 시 상태변경
    void updatePost(int postIdx) throws DataAccessException;
    
    //장바구니에 추가된 상품 가져오기
    List<Post> cartList(String buyerId);
    
	// 거래 내역 확인 -> 로그인한 해당 user가 buyerId이고, 거래상태가 decide 경우의 모든 레코드 가져오기
	List<Post> transactionsCheckList(String buyerId);
	
	//장바구니 삭제 시 상태변경
	void deleteCartPost(int postIdx);
}
