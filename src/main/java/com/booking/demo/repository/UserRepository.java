package com.booking.demo.repository;

import com.booking.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>, PagingAndSortingRepository<User, Integer> {
}
