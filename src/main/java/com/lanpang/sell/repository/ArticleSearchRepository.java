package com.lanpang.sell.repository;

import com.lanpang.sell.dataobject.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @program: mysell
 * @description:
 * @author: yanghao
 * @create: 2018-10-18 08:56
 **/
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, Long> {
    public List<Article> findByTitle(String title);


    /**
     * AND 语句查询
     *
     * @param tile
     * @param clickCount
     * @return
     */
    List<Article> findByTitleAndClickCount(String tile, Integer clickCount);
    /**
     * OR 语句查询
     *
     * @param tile
     * @param clickCount
     * @return
     */
    List<Article> findByTitleOrClickCount(String tile, Integer clickCount);
    /**
     * 查询文章内容分页
     *
     * 等同于下面代码
     * @Query("{\"bool\" : {\"must\" : {\"term\" : {\"content\" : \"?0\"}}}}")
     * Page<Article> findByContent(String content, Pageable pageable);
     *
     * @param content
     * @param page
     * @return
     */
    Page<Article> findByContent(String content, Pageable page);
    /**
     * NOT 语句查询
     *
     * @param content
     * @param page
     * @return
     */
    Page<Article> findByContentNot(String content, Pageable page);
    /**
     * LIKE 语句查询
     *
     * @param content
     * @param page
     * @return
     */
    Page<Article> findByContentLike(String content, Pageable page);

}
