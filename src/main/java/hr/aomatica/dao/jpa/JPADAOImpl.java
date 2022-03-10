package hr.aomatica.dao.jpa;

import hr.aomatica.dao.DAO;
import hr.aomatica.dao.DAOException;
import hr.aomatica.model.addressbook.Email;
import hr.aomatica.model.equipment.*;
import hr.aomatica.model.portal.*;
import hr.aomatica.model.user.UGroup;
import hr.aomatica.model.user.User;

import java.util.List;

public class JPADAOImpl implements DAO {

    @Override
    public List<Email> findAllEmails() throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("from Email", Email.class).getResultList();
    }

    @Override
    public List<User> findAllUsers() throws DAOException{
        return JPAEMProvider.getEntityManager().createQuery("from User ", User.class).getResultList();
    }

    @Override
    public User findUser(Long id) throws DAOException {
        return JPAEMProvider.getEntityManager().getReference(User.class, id);
    }

    @Override
    public List<User> findUserByEmail(String email) throws DAOException {
        return JPAEMProvider.getEntityManager().createNamedQuery("User.upitPoEmailu", User.class)
                .setParameter("e", email)
                .getResultList();
    }

    @Override
    public List<User> findUserByNick(String nick) throws DAOException {
        return JPAEMProvider.getEntityManager().createNamedQuery("User.upitPoNicku", User.class)
                .setParameter("n", nick)
                .getResultList();
    }

    @Override
    public List<User> findUserByFirstAndLast(String firstName, String lastName) throws DAOException {
        return JPAEMProvider.getEntityManager().createNamedQuery("User.upitPoImenu", User.class)
                .setParameter("f", firstName)
                .setParameter("l", lastName)
                .getResultList();
    }

    @Override
    public void saveUser(User user) throws DAOException {
        JPAEMProvider.getEntityManager().persist(user);
    }

    @Override
    public void updateUser(User user) throws DAOException {
        JPAEMProvider.getEntityManager().merge(user);
    }

    @Override
    public void deleteUser(User user) throws DAOException {
        JPAEMProvider.getEntityManager().remove(user);
    }

    @Override
    public Article findArticle(long id) throws DAOException {
        return JPAEMProvider.getEntityManager().getReference(Article.class, id);
    }

    @Override
    public List<Article> findLimitedArticlesOfCategory(long categoryId, int limit, int offset) throws DAOException {
        ArticleCategory c = findCategory(categoryId);
        return JPAEMProvider.getEntityManager().createNamedQuery("Article.upitPoKategoriji", Article.class)
                .setParameter("c", c)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Article> findAllArticlesOfCategory(Long categoryId) throws DAOException{
        ArticleCategory c = findCategory(categoryId);
        return JPAEMProvider.getEntityManager().createNamedQuery("Article.upitPoKategoriji", Article.class)
                .setParameter("c", c)
                .getResultList();

    }

    @Override
    public List<Article> findFilteredArticlesOfCategory(long categoryId, String filter) throws DAOException{
        ArticleCategory c = findCategory(categoryId);
        return JPAEMProvider.getEntityManager().createNamedQuery("Article.filterPoKategoriji", Article.class)
                .setParameter("c", c)
                .setParameter("filter", filter)
                .getResultList();
    }

    @Override
    public List<Article> findNonConfirmedAndNonVisibleArticlesOfCategory(long categoryId) throws DAOException {
        ArticleCategory c = findCategory(categoryId);
        return JPAEMProvider.getEntityManager().createNamedQuery("Article.notConfirmedAndNotVisible", Article.class)
                .setParameter("c", c)
                .getResultList();
    }

    @Override
    public List<Article> findPersonalArticlesOfCategory(long categoryId, User currentUser) throws DAOException {
        ArticleCategory c = findCategory(categoryId);
        return JPAEMProvider.getEntityManager().createNamedQuery("Article.poKorisnikuiKategoriji", Article.class)
                .setParameter("c", c)
                .setParameter("u", currentUser)
                .getResultList();
    }

    @Override
    public long findNumberByCategory(long categoryId)  throws DAOException{
        ArticleCategory c = findCategory(categoryId);
        return JPAEMProvider.getEntityManager().createNamedQuery("Article.brojPoKategoriji", Long.class)
                .setParameter("c", c)
                .getSingleResult();
    }

    @Override
    public void saveArticle(Article a) throws DAOException {
        JPAEMProvider.getEntityManager().persist(a);
    }

    @Override
    public void updateArticle(Article a) throws DAOException {
        a.update(); //zbog razlika u bazi
        JPAEMProvider.getEntityManager().merge(a);
    }

    @Override
    public void deleteArticle(Article a) throws DAOException {
        JPAEMProvider.getEntityManager().remove(a);
    }

    @Override
    public ArticleCategory findCategory(Long id) throws DAOException{
        return JPAEMProvider.getEntityManager().getReference(ArticleCategory.class, id);
    }

    @Override
    public List<News> findAllNews() throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("from News n ORDER BY n.writetime desc ", News.class).getResultList();
    }

    @Override
    public List<News> findLimitedNumberOfNews(int limit, int offset) throws DAOException{
        return JPAEMProvider.getEntityManager().createQuery("from News n order by n.writetime desc ", News.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<News> findNonConfirmedAndNonVisibleNews() throws DAOException {
        return JPAEMProvider.getEntityManager().createNamedQuery("News.notConfirmedAndNotVisible", News.class).getResultList();
    }

    @Override
    public News findNews(long id) throws DAOException {
        return JPAEMProvider.getEntityManager().getReference(News.class, id);
    }

    @Override
    public void saveNews(News n) throws DAOException{
        JPAEMProvider.getEntityManager().persist(n);
    }

    @Override
    public void updateNews(News n) throws DAOException{
        n.update(); //zbog promjene u bazi
        JPAEMProvider.getEntityManager().merge(n);
    }

    @Override
    public void deleteNews(News n) throws DAOException{
        JPAEMProvider.getEntityManager().remove(n);
    }

    @Override
    public long findNumberOfNews() throws DAOException {
        return JPAEMProvider.getEntityManager().createNamedQuery("News.broj", Long.class).getSingleResult();
    }

    @Override
    public List<NewsCategory> findAllNewsCategories() throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("from NewsCategory ", NewsCategory.class).getResultList();
    }

    @Override
    public NewsCategory findNewsCategory(long id) throws DAOException {
        return JPAEMProvider.getEntityManager().getReference(NewsCategory.class, id);
    }

    @Override
    public List<UGroup> findAllUGroups() throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("from UGroup ", UGroup.class).getResultList();
    }

    @Override
    public Format findFormat(long id) throws DAOException {
        return JPAEMProvider.getEntityManager().getReference(Format.class, id);
    }

    @Override
    public List<Format> findAllFormats() throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("from Format", Format.class).getResultList();
    }

    @Override
    public List<Item> findAllItems() throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("from Item", Item.class).getResultList();
    }

    @Override
    public Item findItem(long id) throws DAOException {
        return JPAEMProvider.getEntityManager().getReference(Item.class, id);
    }

    @Override
    public Long findNumberOfActiveItems(Item item) throws DAOException {
        Long q = JPAEMProvider.getEntityManager().createNamedQuery("Item.upitZaPosudene", Long.class).setParameter("i", item).getSingleResult();
        return q != null ? q : 0;
    }

    @Override
    public Long findNumberOfActiveItemsNotFromThisTransaction(Item item, Long transactionId) {
        Long q = JPAEMProvider.getEntityManager().createNamedQuery("Item.upitZaPosudeneOsimOveTransakcije", Long.class).
                setParameter("i", item)
                .setParameter("trans_id", transactionId)
                .getSingleResult();
        return q != null ? q : 0;
    }

    @Override
    public void saveItem(Item item) throws DAOException {
        JPAEMProvider.getEntityManager().persist(item);
    }

    @Override
    public void updateItem(Item item) throws DAOException {
        JPAEMProvider.getEntityManager().merge(item);
    }

    @Override
    public void deleteItem(Item item) throws DAOException {
        JPAEMProvider.getEntityManager().remove(item);
    }

    @Override
    public void deleteAllItems() throws DAOException {
        JPAEMProvider.getEntityManager().createQuery("delete from Item ").executeUpdate();
    }

    @Override
    public List<Transaction> findAllTransactions() throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("from Transaction ", Transaction.class).getResultList();
    }

    @Override
    public List<Transaction> findNonReturnedTransactions() throws DAOException {
        return JPAEMProvider.getEntityManager().createNamedQuery("Transaction.upitNevracenih", Transaction.class).getResultList();
    }

    @Override
    public Transaction findTransaction(long id) throws DAOException {
        return JPAEMProvider.getEntityManager().getReference(Transaction.class,  id);
    }

    @Override
    public void saveTransaction(Transaction t) throws DAOException {
        JPAEMProvider.getEntityManager().persist(t);
    }

    @Override
    public void updateTransaction(Transaction t) throws DAOException {
        JPAEMProvider.getEntityManager().merge(t);
    }

    @Override
    public void deleteAllTransactions() throws DAOException {
        JPAEMProvider.getEntityManager().createQuery("delete from Transaction ").executeUpdate();
    }

    @Override
    public void deleteAllTransactionEntries() throws DAOException {
        JPAEMProvider.getEntityManager().createQuery("delete from TransactionEntry ").executeUpdate();
    }

    @Override
    public void saveEquipmentGroup(Group g) throws DAOException {
        JPAEMProvider.getEntityManager().persist(g);
    }

    @Override
    public List<Group> findAllGroups() throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery(" from Group ", Group.class).getResultList();
    }

    @Override
    public Group findGroup(long id) throws DAOException {
        return JPAEMProvider.getEntityManager().getReference(Group.class, id);
    }

    @Override
    public List<Group> findEquipmentGroupByName(String name) throws DAOException {
        return JPAEMProvider.getEntityManager().createNamedQuery("Group.upitPoImenu", Group.class)
                .setParameter("n", name)
                .getResultList();
    }

    @Override
    public void deleteAllEquipmentGroups() throws DAOException {
        JPAEMProvider.getEntityManager().createQuery("delete from Group ").executeUpdate();
    }

    @Override
    public void saveEquipmentType(Type t) throws DAOException {
        JPAEMProvider.getEntityManager().persist(t);
    }

    @Override
    public List<Type> findAllTypes() throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("from Type ", Type.class).getResultList();
    }

    @Override
    public Type findType(long id) throws DAOException {
        return JPAEMProvider.getEntityManager().getReference(Type.class, id);
    }

    @Override
    public List<Type> findEquipmentTypeByNameGroupAndVendor(String name, Group g, Vendor v) throws DAOException {
        return JPAEMProvider.getEntityManager().createNamedQuery("Type.upitPoImenuVendoruGrupi", Type.class)
                .setParameter("n", name)
                .setParameter("g", g)
                .setParameter("v", v)
                .getResultList();
    }

    @Override
    public void deleteAllEquipmentTypes() throws DAOException {
        JPAEMProvider.getEntityManager().createQuery("delete from Type ").executeUpdate();
    }

    @Override
    public void saveEquipmentVendor(Vendor v) throws DAOException {
        JPAEMProvider.getEntityManager().persist(v);
    }

    @Override
    public List<Vendor> findAllVendors() throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("from Vendor ", Vendor.class).getResultList();
    }

    @Override
    public Vendor findVendor(long id) throws DAOException {
        return JPAEMProvider.getEntityManager().getReference(Vendor.class, id);
    }

    @Override
    public List<Vendor> findEquipmentVendorByName(String name) throws DAOException {
        return JPAEMProvider.getEntityManager().createNamedQuery("Vendor.upitPoImenu", Vendor.class)
                .setParameter("n", name)
                .getResultList();
    }

    @Override
    public void deleteAllEquipmentVendors() throws DAOException {
        JPAEMProvider.getEntityManager().createQuery("delete from Vendor ").executeUpdate();
    }
}