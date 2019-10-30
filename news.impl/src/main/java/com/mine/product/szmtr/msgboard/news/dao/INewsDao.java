package com.mine.product.szmtr.msgboard.news.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mine.product.szmtr.msgboard.news.model.News;

public interface INewsDao extends JpaRepository<News, String>{

}
