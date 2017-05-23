/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import spring.entity.Blogs;

/**
 *
 * @author Phan
 */
@Stateless
public class BlogsSB implements BlogsSBLocal {

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Blogs> getAllBlogs() {
        Query q = getEntityManager().createQuery("SELECT b FROM Blogs b WHERE b.status = 0 ORDER BY b.blogViews DESC", Blogs.class);
        return q.getResultList();
    }

    @Override
    public List<Blogs> getAllBlogsIndex() {
        Query q = getEntityManager().createQuery("SELECT b FROM Blogs b WHERE b.status = 0 ORDER BY b.blogID DESC", Blogs.class);
        return q.getResultList();
    }

    @Override
    public List<Blogs> getAllBlogsIndexMonth() {
        Query q = getEntityManager().createQuery("SELECT b FROM Blogs b WHERE b.status = 0 ORDER BY b.postedDate DESC", Blogs.class);
        return q.getResultList();
    }

    @Override
    public List<Blogs> getAllBlogsAdmin() {
        Query q = getEntityManager().createQuery("SELECT b FROM Blogs b", Blogs.class);
        return q.getResultList();
    }

    @Override
    public List<Blogs> getAllBlogsByMonth(int month) {
        Query q = getEntityManager().createQuery("SELECT b FROM Blogs b WHERE FUNCTION('MONTH',b.postedDate) = FUNCTION('MONTH',:month)", Blogs.class);
        q.setParameter("month", "1990-" + String.valueOf(month) + "-20");
        return q.getResultList();
    }

    @Override
    public List<Blogs> getListBlogsByCategory(int blogCateID) {
        Query q = em.createQuery("SELECT b FROM Blogs b WHERE b.blogCategory.blogCateID = :blogCateID", Blogs.class);
        q.setParameter("blogCateID", blogCateID);
        return q.getResultList();
    }

    @Override
    public boolean blogAdd(Blogs newBlogs) {
        try {
            em.persist(newBlogs);
            getEntityManager().flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Blogs findBlogsByID(int id) {
        Query q = em.createQuery("SELECT b FROM Blogs b WHERE b.blogID = :blogID", Blogs.class);
        q.setParameter("blogID", id);
        return (Blogs) q.getSingleResult();
    }

    @Override
    public boolean editBlogs(Blogs targetBlogs) {
        try {
            getEntityManager().merge(targetBlogs);
            getEntityManager().flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Blogs> findBlogsByTitle(String blogTitle, List<Integer> monthList) {
        String adSearchHead = " AND ( ";
        String adSearchFoot = " )";
        String adSearchMonthHead = "MONTH(b.postedDate) = MONTH('2017-";
        String adSearchMonthFoot = "-15')";
        String orString = "OR";
        if (monthList == null) {
            Query q = getEntityManager().createQuery("SELECT b FROM Blogs b WHERE b.blogTitle LIKE :blogTitle ", Blogs.class);
            q.setParameter("blogTitle", "%" + blogTitle + "%");
            return q.getResultList();
        } else if (monthList.size() == 1) {
            Query q = getEntityManager().createQuery("SELECT b FROM Blogs b WHERE b.blogTitle LIKE :blogTitle"
                    + adSearchHead + adSearchMonthHead + monthList.get(0).toString() + adSearchMonthFoot + adSearchFoot, Blogs.class);
            q.setParameter("blogTitle", "%" + blogTitle + "%");
            return q.getResultList();
        } else {

            return null;
        }
    }

    @Override
    public int deleteBlog(Blogs blog) {
        try {
            if (!getEntityManager().contains(blog)) {
                blog = getEntityManager().merge(blog);
            }
            getEntityManager().remove(blog);
            getEntityManager().flush();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Integer getAllNumberBlogsInCate(int blogCateID) {
        Query q = getEntityManager().createQuery("SELECT COUNT(b.blogID) FROM Blogs b WHERE b.blogCategory.blogCateID = :blogCateID", Blogs.class);
        q.setParameter("blogCateID", blogCateID);
        return Integer.parseInt(q.getSingleResult().toString());
    }

}
