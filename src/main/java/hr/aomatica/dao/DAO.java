package hr.aomatica.dao;

import hr.aomatica.model.addressbook.Email;
import hr.aomatica.model.equipment.*;
import hr.aomatica.model.portal.*;
import hr.aomatica.model.user.UGroup;
import hr.aomatica.model.user.User;
import jdk.jfr.Category;

import java.util.List;

public interface DAO {

    /**
     * Lists all emails
     * @return
     */
    List<Email> findAllEmails() throws DAOException;

    /**
     * Lists all users
     * @return
     */
    List<User> findAllUsers() throws DAOException;

    /**
     * Finds user by given ID. There is only one sample of that user which is being returned
     * @return
     */
    User findUser(Long id) throws DAOException;

    /**
     * Finds user by email
     * @return
     */
    List<User> findUserByEmail(String email) throws DAOException;

    /**
     * Finds user by nick
     * @param nick
     * @return
     * @throws DAOException
     */
    List<User> findUserByNick(String nick) throws DAOException;


    /**
     * Finds user by first and last name
     * @param firstName
     * @param lastName
     * @return
     * @throws DAOException
     */
    List<User> findUserByFirstAndLast(String firstName, String lastName) throws DAOException;

    /**
     * Saves user
     * @param user
     * @throws DAOException
     */
    void saveUser(User user) throws DAOException;

    /**
     * Updates user
     * @param user
     */
    void updateUser(User user) throws DAOException;

    /**
     *
     * @param user
     */
    void deleteUser(User user) throws DAOException;

    /**
     * Finds article from given id
     * @param id
     * @return
     */
    Article findArticle(long id) throws DAOException;

    /**
     * Finds limited number artices of given category
     * @param categoryId
     * @return
     */
    List<Article> findLimitedArticlesOfCategory(long categoryId, int limit, int offset) throws DAOException;

    /**
     * finds all articles of given category
     * @param categoryId
     * @return
     */
    List<Article> findAllArticlesOfCategory(Long categoryId) throws DAOException;


    /**
     * Finds articles of category where title or body containt filer
     * @param categoryId
     * @param filter
     * @return
     */
    List<Article> findFilteredArticlesOfCategory(long categoryId, String filter) throws DAOException;

    /**
     * Finds not confirmed and not visible articles of given category
     * @param categoryId
     * @return
     * @throws DAOException
     */
    List<Article> findNonConfirmedAndNonVisibleArticlesOfCategory(long categoryId) throws DAOException;

    /**
     * Finds personal articles of given category
     * @param categoryId
     * @param currentUser
     * @return
     * @throws DAOException
     */
    List<Article> findPersonalArticlesOfCategory(long categoryId, User currentUser) throws DAOException;
    /**
     * Finds number of visible articles of given category
     * @param categoryId
     * @return
     */
    long findNumberByCategory(long categoryId);

    /**
     * Saves article
     * @param a
     * @throws DAOException
     */
    void saveArticle(Article a) throws DAOException;

    /**
     * Updates article
     * @param a
     * @throws DAOException
     */
    void updateArticle(Article a) throws DAOException;

    /**
     * Deletes article
     * @param a
     * @throws DAOException
     */
    void deleteArticle(Article a) throws DAOException;

    /**
     * Finds category from id
     * @param id
     * @return
     */
    ArticleCategory findCategory(Long id) throws DAOException;

    /**
     * Finds all news
     * @return
     * @throws DAOException
     */
    List<News> findAllNews() throws DAOException;


    /**
     * Finds limited number of news with given offset
     * @param limit
     * @param offset
     * @return
     */
    List<News> findLimitedNumberOfNews(int limit, int offset) throws DAOException;

    /**
     * Finds not confirmed and not visible news
     * @return
     * @throws DAOException
     */
    List<News> findNonConfirmedAndNonVisibleNews() throws DAOException;
    /**
     * Finds news
     * @param id
     * @return
     * @throws DAOException
     */
    News findNews(long id) throws DAOException;

    /**
     * Saves given news
     * @param n
     */
    void saveNews(News n);

    /**
     * Updates news
     * @param n
     */
    void updateNews(News n);

    /**
     * Deletes news
     * @param n
     */
    void deleteNews(News n);
    /**
     * Finds total number of news articles
     * @return
     * @throws DAOException
     */
    long findNumberOfNews() throws DAOException;

    /**
     * Finds all news categories
     * @return
     * @throws DAOException
     */
    List<NewsCategory> findAllNewsCategories() throws DAOException;

    /**
     * Finds news category from given id
     * @param id
     * @return
     * @throws DAOException
     */
    NewsCategory findNewsCategory(long id) throws DAOException;

    /**
     * Lists all user groups
     * @return
     */
    List<UGroup> findAllUGroups() throws DAOException;

    /**
     * Finds format with id
     * @param id
     * @return
     * @throws DAOException
     */
    Format findFormat(long id) throws DAOException;

    /**
     * Lists all formats
     * @return
     * @throws DAOException
     */
    List<Format> findAllFormats() throws DAOException;

    /**
     * Finds all equipment items
     * @return
     * @throws DAOException
     */
    List<Item> findAllItems() throws DAOException;

    /**
     * Finds item
     * @return
     * @throws DAOException
     */
    Item findItem(long id) throws DAOException;

    /**
     * Finds number of items currently borrowed
     * @param item
     * @return
     * @throws DAOException
     */
    Long findNumberOfActiveItems(Item item) throws DAOException;

    /**
     * Finds number of items currently borrowed outside of this transaction
     * @param item
     * @param transactionId
     * @return
     */
    Long findNumberOfActiveItemsNotFromThisTransaction(Item item, Long transactionId);
    /**
     * Saves item
     * @param item
     * @throws DAOException
     */
    void saveItem(Item item) throws DAOException;

    /**
     * Updates item
     * @param item
     * @throws DAOException
     */
    void updateItem(Item item) throws DAOException;

    /**
     * Deletes item
     * @param item
     * @throws DAOException
     */
    void deleteItem(Item item) throws DAOException;

    /**
     * Deletes all items
     * @throws DAOException
     */
    void deleteAllItems() throws DAOException;

    /**
     * Finds all transactions
     * @return
     * @throws DAOException
     */
    List<Transaction> findAllTransactions() throws DAOException;

    /**
     * Finds all non returned transactions
     * @return
     * @throws DAOException
     */
    List<Transaction> findNonReturnedTransactions() throws DAOException;

    /**
     * Finds transaction with id
     * @return
     * @throws DAOException
     */
    Transaction findTransaction(long id) throws DAOException;

    /**
     * Saves transaction
     * @param t
     * @throws DAOException
     */
    void saveTransaction(Transaction t) throws DAOException;

    /**
     * Updates transaction t
     * @param t
     * @throws DAOException
     */
    void updateTransaction(Transaction t) throws DAOException;

    /**
     * Deletes all transactions
     * @throws DAOException
     */
    void deleteAllTransactions() throws DAOException;

    /**
     * Deletes all transaction entries
     * @throws DAOException
     */
    void deleteAllTransactionEntries() throws DAOException;

    /**
     * Finds all equipment groups
     * @return
     * @throws DAOException
     */
    List<Group> findAllGroups() throws DAOException;

    /**
     * Finds equipment group by id
     * @param id
     * @return
     * @throws DAOException
     */
    Group findGroup(long id) throws DAOException;

    /**
     * Saves equipment group
     * @throws DAOException
     */
    void saveEquipmentGroup(Group g) throws DAOException;

    /**
     * Finds equipment group by name
     * @param name
     * @return
     * @throws DAOException
     */
    List<Group> findEquipmentGroupByName(String name) throws DAOException;

    /**
     * Deletes all equipmentGroups
     * @throws DAOException
     */
    void deleteAllEquipmentGroups() throws DAOException;

    /**
     * Saves equipment type
     * @throws DAOException
     */
    void saveEquipmentType(Type t) throws DAOException;

    /**
     * Finds all equipment types
     * @return
     * @throws DAOException
     */
    List<Type> findAllTypes() throws DAOException;

    /**
     * Finds type by id
     * @param id
     * @return
     * @throws DAOException
     */
    Type findType(long id) throws DAOException;

    /**
     * Finds equipment type by name
     * @param name
     * @return
     * @throws DAOException
     */
    List<Type> findEquipmentTypeByNameGroupAndVendor(String name, Group g, Vendor v) throws DAOException;

    /**
     * Deletes all equipment types
     * @throws DAOException
     */
    void deleteAllEquipmentTypes() throws DAOException;

    /**
     * Finds all vendors
     * @return
     * @throws DAOException
     */
    List<Vendor> findAllVendors() throws DAOException;

    /**
     * Finds vendor by id
     * @param id
     * @return
     * @throws DAOException
     */
    Vendor findVendor(long id) throws DAOException;
    /**
     * Saves equipment vendor
     * @throws DAOException
     */
    void saveEquipmentVendor(Vendor v) throws DAOException;

    /**
     * Finds equipment vendor by name
     * @param name
     * @return
     * @throws DAOException
     */
    List<Vendor> findEquipmentVendorByName(String name) throws DAOException;

    /**
     * Deletes all equipment vendors
     * @throws DAOException
     */
    void deleteAllEquipmentVendors() throws DAOException;
}