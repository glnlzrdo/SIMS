package com.ever.POS.best.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ever.POS.best.enums.TransactionType;
import com.ever.POS.best.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseController {

	private static Connection conn = null;

	/* Database Connection Manager */
	private static void dbConnect() {
		if (conn == null)
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost/sims?user=root&password=12345&useSSL=true");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/* Database Disconnection */
	public static void dbClose() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* Get all products from product table and put into a List */
	public static List<Product> openInventoryDatabase() {
		dbConnect();
		Statement stmt = null;
		ResultSet rs = null;
		List<Product> prods = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(GET_PRODUCTS_FROM_DB);
			while (rs.next()) {
				int productCode = rs.getInt("product_code");
				String productName = rs.getString("product_name");
				String productUnit = rs.getString(4);
				String productDescription = rs.getString(5);
				double priceForPurchase = rs.getDouble(6);
				double priceForSales = rs.getDouble(7);
				int stockQuantity = rs.getInt(8);
				prods.add(new Product(productCode, productName, productUnit, productDescription, priceForPurchase,
						priceForSales, stockQuantity));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		// dbClose();
		return prods;
	}

	/*
	 * Get all purchase transactions from purchases or sales table and put into
	 * a List
	 */
	public static List<Transaction> openTransactionDatabase(TransactionType transactionType) {
		List<Transaction> transactions = new ArrayList<>();
		dbConnect();
		Statement stmt = null;
		ResultSet rs = null;
		final String OPEN_TRANSACTIONS_FROM_DB;
		if (transactionType == TransactionType.PURCHASE)
			OPEN_TRANSACTIONS_FROM_DB = "SELECT * FROM purchase ORDER BY purchase_id DESC";
		else
			OPEN_TRANSACTIONS_FROM_DB = "SELECT * FROM sales ORDER BY sales_id DESC";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(OPEN_TRANSACTIONS_FROM_DB);
			while (rs.next()) {
				int transactionNumber = rs.getInt(1);
				String transactionDate = rs.getString(2);
				String nameOfClient = getClient(rs.getInt(3), transactionType).getName();
				double cashGiven = rs.getDouble(4);
				ObservableList<Product> productList = getProductTransaction(rs.getInt(1), transactionType);

				transactions.add(new Transaction(transactionNumber, transactionDate, nameOfClient, cashGiven, cashGiven,
						productList.size(), productList));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return transactions;
	}

	/*
	 * Get all purchase transactions from purchases or sales table where client
	 * name is filtered and put into a List
	 */
	public static List<Transaction> getTransactionsFilteredByClientName(String clientName,
			TransactionType transactionType) {
		List<Transaction> transactions = new ArrayList<>();
		dbConnect();
		Statement stmt = null;
		ResultSet rs = null;
		int clientId = getClientId(clientName, transactionType);
		final String OPEN_TRANSACTIONS_FROM_DB;
		if (transactionType == TransactionType.PURCHASE)
			OPEN_TRANSACTIONS_FROM_DB = "SELECT * FROM purchase WHERE purchase_client_id = " + clientId
					+ " ORDER BY purchase_id DESC";
		else
			OPEN_TRANSACTIONS_FROM_DB = "SELECT * FROM sales WHERE sales_client_id = " + clientId
					+ " ORDER BY sales_id DESC";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(OPEN_TRANSACTIONS_FROM_DB);
			while (rs.next()) {
				int transactionNumber = rs.getInt(1);
				String transactionDate = rs.getString(2);
				String nameOfClient = getClient(rs.getInt(3), transactionType).getName();
				double cashGiven = rs.getDouble(4);
				ObservableList<Product> productList = getProductTransaction(rs.getInt(1), transactionType);

				transactions.add(new Transaction(transactionNumber, transactionDate, nameOfClient, cashGiven, cashGiven,
						productList.size(), productList));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return transactions;
	}

	/*
	 * Get all purchase transactions from purchases or sales table where date is
	 * filtered and put into a List
	 */
	public static List<Transaction> getTransactionsFilteredByDate(String startDate, String endDate,
			TransactionType transactionType) {
		List<Transaction> transactions = new ArrayList<>();
		dbConnect();
		Statement stmt = null;
		ResultSet rs = null;
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date start = (Date) sdf.parse(startDate);
		// Date end = (Date) sdf.parse(endDate);
		final String OPEN_TRANSACTIONS_FROM_DB;
		if (transactionType == TransactionType.PURCHASE)
			OPEN_TRANSACTIONS_FROM_DB = "SELECT * FROM purchase WHERE DATE(purchase_date) >= DATE('" + startDate
					+ "') AND DATE(purchase_date) <= DATE('" + endDate + "') ORDER BY purchase_id DESC";
		else
			OPEN_TRANSACTIONS_FROM_DB = "SELECT * FROM sales WHERE DATE(sales_date) >= DATE('" + startDate
					+ "') AND DATE(sales_date) <= DATE('" + endDate + "') ORDER BY sales_id DESC";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(OPEN_TRANSACTIONS_FROM_DB);
			while (rs.next()) {
				int transactionNumber = rs.getInt(1);
				String transactionDate = rs.getString(2);
				String nameOfClient = getClient(rs.getInt(3), transactionType).getName();
				double cashGiven = rs.getDouble(4);
				ObservableList<Product> productList = getProductTransaction(rs.getInt(1), transactionType);

				transactions.add(new Transaction(transactionNumber, transactionDate, nameOfClient, cashGiven, cashGiven,
						productList.size(), productList));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return transactions;
	}

	/* Get Specific Client */
	public static Client getClient(int clientId, TransactionType transactionType) {
		Statement stmt = null;
		ResultSet rs = null;
		final String GET_CLIENT;
		if (transactionType == TransactionType.PURCHASE) {
			GET_CLIENT = "SELECT * FROM purchase_client WHERE purchase_client_id = " + clientId;
		} else {
			GET_CLIENT = "SELECT * FROM sales_client WHERE sales_client_id = " + clientId;
		}
		Client client = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(GET_CLIENT);
			while (rs.next()) {
				client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				break;
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return client;
	}

	/*
	 * Get all products from product table in a specific transaction ID from
	 * product_transaction table and put into a List
	 */
	public static ObservableList<Product> getProductTransaction(int transactionId, TransactionType transactionType) {
		dbConnect();
		Statement stmt = null;
		ResultSet rs = null;
		ObservableList<Product> products = FXCollections.observableArrayList();
		final String GET_PRODUCTS_OF_ID;
		if (transactionType == TransactionType.PURCHASE) {
			GET_PRODUCTS_OF_ID = "SELECT * FROM product_transaction WHERE purchase_id = " + transactionId;
		} else {
			GET_PRODUCTS_OF_ID = "SELECT * FROM product_transaction WHERE sales_id = " + transactionId;
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(GET_PRODUCTS_OF_ID);
			while (rs.next()) {
				Product product = getProductViaId(rs.getInt(2));
				product.setSubQuantity(rs.getDouble("quantity"));
				if (transactionType == TransactionType.PURCHASE)
					product.setSubTotal(rs.getDouble("quantity") * product.getPriceForPurchase());
				else
					product.setSubTotal(rs.getDouble("quantity") * product.getPriceForSales());
				products.add(product);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return products;
	}

	/* Get Specific product from product table */
	public static Product getProductViaId(int prodId) {
		dbConnect();
		Statement stmt = null;
		ResultSet rs = null;
		final String GET_PRODUCT = "SELECT * FROM product WHERE product_id = " + prodId;
		Product product = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(GET_PRODUCT);
			while (rs.next()) {
				int productId = rs.getInt(2);
				String productName = rs.getString(3);
				String productUnit = rs.getString(4);
				String productDescription = rs.getString(5);
				double priceForPurchase = rs.getDouble(6);
				double priceForSales = rs.getDouble(7);
				double stockQuantity = rs.getDouble(8);

				product = new Product(productId, productName, productUnit, productDescription, priceForPurchase,
						priceForSales, stockQuantity);
				break;
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return product;
	}

	/* Get Produt using product code */
	public static Product getProductViaCode(int prodCode) {
		dbConnect();
		Statement stmt = null;
		ResultSet rs = null;
		final String GET_PRODUCT = "SELECT * FROM product WHERE product_code = " + prodCode;
		Product product = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(GET_PRODUCT);
			while (rs.next()) {
				int productId = rs.getInt(1);
				int productCode = rs.getInt(2);
				String productName = rs.getString(3);
				String productUnit = rs.getString(4);
				String productDescription = rs.getString(5);
				double priceForPurchase = rs.getDouble(6);
				double priceForSales = rs.getDouble(7);
				double stockQuantity = rs.getDouble(8);

				product = new Product(productCode, productName, productUnit, productDescription, priceForPurchase,
						priceForSales, stockQuantity);
				product.setProductId(productId);
				break;
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return product;
	}

	/* Save purchase transaction related data to DB */
	public static boolean saveTransaction(Transaction transaction, TransactionType transactionType) {
		dbConnect();
		PreparedStatement stmt = null;
		boolean isTransactionOk = true;

		// Format Transaction Date for mysql
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// Could also use java.sql.Date
		// java.sql.Date dat = new java.sql.Date(date.getTime());

		// Get purchase ClientId if existing and insert if client is new
		int purchaseClientId = getClientId(transaction.getNameOfClient(), transactionType);
		if (purchaseClientId == 0) {
			Client newClient = createNewClient(new Client(0, "'" + transaction.getNameOfClient() + "'", null, null),
					transactionType);
			purchaseClientId = newClient.getClientId();
		}

		final String INSERT_NEW_TRANSACTION;
		// Insert to purchase table
		if (transactionType == TransactionType.PURCHASE) {
			INSERT_NEW_TRANSACTION = "INSERT INTO purchase (purchase_id, purchase_date, purchase_client_id, purchase_payment)"
					+ " VALUES (" + transaction.getTransactionNumber() + ",'" + sdf.format(date) + "',"
					+ purchaseClientId + "," + transaction.getTotalAmount() + ")";
		} else {
			INSERT_NEW_TRANSACTION = "INSERT INTO sales (sales_id, sales_date, sales_client_id, sales_payment)"
					+ " VALUES (" + transaction.getTransactionNumber() + ",'" + sdf.format(date) + "',"
					+ purchaseClientId + "," + transaction.getTotalAmount() + ")";
		}
		try {
			stmt = conn.prepareStatement(INSERT_NEW_TRANSACTION);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			isTransactionOk = false;
			e.printStackTrace();
		}
		// Insert to product_transaction table
		boolean isProductTransactionOk = saveProductTransactions(
				(transactionType == TransactionType.PURCHASE ? transaction.getTransactionNumber() : 0),
				transaction.getProductList(),
				(transactionType == TransactionType.RETAIL ? transaction.getTransactionNumber() : 0));

		// Update Product Stock Quantity in product table
		boolean isInventoryStockOk = updateInventoryStock(transaction.getProductList(), transactionType);

		return isTransactionOk && isProductTransactionOk && isInventoryStockOk;
	}

	/* Update stock quantity of products that matches the List */
	public static boolean updateInventoryStock(ObservableList<Product> products, TransactionType transactionType) {
		dbConnect();
		PreparedStatement stmt = null;
		boolean saveStatus = true;
		for (Product product : products) {
			String operation = (transactionType == TransactionType.PURCHASE ? "+ " : "- ");
			final String UPDATE_STOCK = "UPDATE product SET product_quantity = product_quantity " + operation
					+ product.getSubQuantity() + " WHERE product_code = " + product.getProductCode();
			try {
				stmt = conn.prepareStatement(UPDATE_STOCK);
				stmt.executeUpdate();
				stmt.close();
			} catch (SQLException e) {
				saveStatus = false;
				e.printStackTrace();
			}
		}
		return saveStatus;
	}

	// Save products on 1 transaction
	public static boolean saveProductTransactions(int purchaseId, ObservableList<Product> products, int salesId) {
		dbConnect();
		PreparedStatement stmt = null;
		boolean saveStatus = true;
		for (Product product : products) {
			final String INSERT_NEW_PRODUCT_TRANSACTION = "INSERT INTO product_transaction (purchase_id, product_id, sales_id, quantity)"
					+ " VALUES (" + (purchaseId == 0 ? null : purchaseId) + ","
					+ getProductViaCode(product.getProductCode()).getProductId() + "," + (salesId == 0 ? null : salesId)
					+ "," + product.getSubQuantity() + ")";
			try {
				stmt = conn.prepareStatement(INSERT_NEW_PRODUCT_TRANSACTION);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				saveStatus = false;
				e.printStackTrace();
			}
		}
		return saveStatus;
	}

	/* Save new product to product table */
	public static boolean createNewProduct(Product product) {
		dbConnect();
		PreparedStatement stmt = null;
		boolean saveStatus = true;
		final String INSERT_NEW_PRODUCT = "INSERT INTO product "
				+ "(product_id, product_code, product_name, product_unit, product_description, product_purprice, product_retprice, product_quantity)"
				+ " VALUES (0, " + product.getProductCode() + ", UPPER('" + product.getProductName().replace("'", "\\'")
				+ "'), UPPER('" + product.getProductUnit().replace("'", "\\'") + "'), UPPER('"
				+ product.getProductDescription().replace("'", "\\'") + "'), " + product.getPriceForPurchase() + ", '"
				+ product.getPriceForSales() + "', '" + product.getStockQuantity() + "')";
		try {
			stmt = conn.prepareStatement(INSERT_NEW_PRODUCT);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			saveStatus = false;
			e.printStackTrace();
		}
		return saveStatus;
	}

	/* Edit product via product_code */
	public static boolean updateProductDetails(Product product) {
		dbConnect();
		PreparedStatement stmt = null;
		boolean saveStatus = true;
		final String UPDATE_PRODUCT = "UPDATE product SET product_code='" + product.getProductCode()
				+ "', product_name=UPPER('" + product.getProductName().replace("'", "\\'") + "'), product_unit=UPPER('"
				+ product.getProductUnit() + "'), product_description=UPPER('"
				+ product.getProductDescription().replace("'", "\\'") + "'), product_purprice="
				+ product.getPriceForPurchase() + ", product_retprice=" + product.getPriceForSales()
				+ ", product_quantity=" + product.getStockQuantity() + " WHERE product_code="
				+ product.getProductCode();
		try {
			stmt = conn.prepareStatement(UPDATE_PRODUCT);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			saveStatus = false;
			e.printStackTrace();
		}
		return saveStatus;
	}

	/* Delete product via product_code */
	public static boolean deleteProduct(Product product) {
		dbConnect();
		PreparedStatement stmt = null;
		boolean saveStatus = true;
		final String DELETE_PRODUCT = "DELETE FROM product WHERE product_code=" + product.getProductCode();
		try {
			stmt = conn.prepareStatement(DELETE_PRODUCT);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			saveStatus = false;
			e.printStackTrace();
		}
		return saveStatus;
	}

	/* Get purchaseClientId using the Name of the client */
	public static int getClientId(String clientName, TransactionType transactionType) {
		dbConnect();
		Statement stmt = null;
		ResultSet rs = null;
		final String GET_CLIENT;
		if (transactionType == TransactionType.PURCHASE)
			GET_CLIENT = "SELECT * FROM purchase_client WHERE UPPER(name) = UPPER('" + clientName + "')";
		else
			GET_CLIENT = "SELECT * FROM sales_client WHERE UPPER(name) = UPPER('" + clientName + "')";

		int clientId = 0;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(GET_CLIENT);
			while (rs.next()) {
				clientId = rs.getInt(1);
				break;
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			clientId = 0;
		}
		return clientId;
	}

	/* Create new client based on client type */
	public static Client createNewClient(Client client, TransactionType transactionType) {
		dbConnect();
		PreparedStatement stmt = null;
		final String INSERT_NEW_CLIENT;
		if (transactionType == TransactionType.PURCHASE)
			INSERT_NEW_CLIENT = "INSERT INTO purchase_client (purchase_client_id, name, address, telephone)";
		else
			INSERT_NEW_CLIENT = "INSERT INTO sales_client (sales_client_id, name, address, telephone)";

		try {
			stmt = conn.prepareStatement(INSERT_NEW_CLIENT + "VALUES (" + client.getClientId() + "," + client.getName()
					+ "," + client.getAddress() + "," + client.getTelephone() + ")");
			stmt.execute();
			stmt.close();
			// Get latest entry from client table
			return latestEntryFromClient(TransactionType.PURCHASE);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* Get latest client entry based on client type */
	public static Client latestEntryFromClient(TransactionType transactionType) {
		dbConnect();
		Statement stmt = null;
		ResultSet rs = null;
		final String GET_LATEST_CLIENT;
		if (transactionType == TransactionType.PURCHASE)
			GET_LATEST_CLIENT = "SELECT * FROM purchase_client ORDER BY purchase_client_id DESC LIMIT 1";
		else
			GET_LATEST_CLIENT = "SELECT * FROM sales_client ORDER BY sales_client_id DESC LIMIT 1";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(GET_LATEST_CLIENT);
			Client client = null;
			while (rs.next()) {
				client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				break;
			}
			stmt.close();
			rs.close();
			return client;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static final String GET_PRODUCTS_FROM_DB = "SELECT * FROM product";

	public static ObservableList<Client> getAllClients(TransactionType transactionType) {
		dbConnect();
		Statement stmt = null;
		ResultSet rs = null;
		final String GET_ALL_CLIENT;
		if (transactionType == TransactionType.PURCHASE)
			GET_ALL_CLIENT = "SELECT * FROM purchase_client";
		else
			GET_ALL_CLIENT = "SELECT * FROM sales_client";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(GET_ALL_CLIENT);
			ObservableList<Client> clients = FXCollections.observableArrayList();
			while (rs.next()) {
				clients.add(new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			stmt.close();
			rs.close();
			return clients;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}