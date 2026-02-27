package org.example.ui;

import org.example.model.Product;
import org.example.service.ProductService;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final ProductService service = new ProductService();
    private final Scanner sc = new Scanner(System.in);

    public void start() {

        while (true) {

            System.out.println(
                    "1 Add\n" +
                            "2 View All\n" +
                            "3 Search Name\n" +
                            "4 Low Stock\n" +
                            "5 Pagination\n" +
                            "6 Stock Value\n" +
                            "0 Exit"
            );

            int ch = sc.nextInt();
            try {
                switch (ch) {

                    case 1:
                        add();
                        break;

                    case 2:
                        viewAll();
                        break;

                    case 3:
                        searchName();
                        break;

                    case 4:
                        lowStock();
                        break;

                    case 5:
                        pagination();
                        break;

                    case 6:
                        System.out.println(service.stockValue());
                        break;

                    case 0:
                        return;

                    default:
                        System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void add() throws Exception {
        Product p = new Product();

        System.out.print("Id: "); p.productId=sc.nextInt(); sc.nextLine();
        System.out.print("Name: "); p.productName=sc.nextLine();
        System.out.print("Category: "); p.category=sc.nextLine();
        System.out.print("Price: "); p.price=sc.nextDouble();
        System.out.print("Qty: "); p.quantity=sc.nextInt();
        System.out.print("Rating: "); p.rating=sc.nextDouble(); sc.nextLine();
        System.out.print("Manufacturer: "); p.manufacturer=sc.nextLine();

        System.out.println(service.add(p));
    }

    private void viewAll() throws Exception {
        List<Product> list=service.all();
        list.forEach(p->System.out.println(p.productId+" "+p.productName));
    }

    private void searchName() throws Exception {
        sc.nextLine();
        System.out.print("Name: ");
        service.searchName(sc.nextLine())
                .forEach(p->System.out.println(p.productName));
    }

    private void lowStock() throws Exception {
        System.out.print("Threshold: ");
        service.lowStock(sc.nextInt())
                .forEach(p->System.out.println(p.productName));
    }

    private void pagination() throws Exception {
        System.out.print("Page: ");
        int p=sc.nextInt();
        service.page(p,5)
                .forEach(x->System.out.println(x.productName));
    }
}
