package com.cristiano.repository;

import com.cristiano.models.User;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<Long, User> {}
