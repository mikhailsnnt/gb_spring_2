package com.sainnt.gb_spring_2;

import com.sainnt.gb_spring_2.repository.ProductRepository;
import com.sainnt.gb_spring_2.service.Cart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class GbSpring2Application {
    private static int nextCartId = 1;
    private static final Map<Integer, Cart> carts = new HashMap<>();
    private static int currentCartId = -1;

    private static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(GbSpring2Application.class, args);
        cartInteractionLoop();
    }

    public static void cartInteractionLoop() {
        System.out.println("""
                Cart interaction commands:
                create cart
                open cart {CartId}
                list carts
                list products             #Prints available products
                cart info                 #Prints current cart id,content
                cart add {ProductId}
                cart remove {ProductId}
                quit
                """);
        String input;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            input = scanner.nextLine().strip();
            if (input.startsWith("create cart")) {
                carts.put(nextCartId, context.getBean(Cart.class) );
                currentCartId = nextCartId++;
            }
            else if(input.startsWith("open cart "))
            {
                try{
                    int cartId = getParam(input,10);
                    if(carts.containsKey(cartId))
                        currentCartId = cartId;
                    else
                        System.out.printf("Cart with id %d not found\n",cartId);
                }
                catch (NumberFormatException e){
                    System.out.println("CartId format exception!");
                }
            }
            else if(input.startsWith("list carts")){
                System.out.printf("Total carts: %d\n",carts.size());
                carts.keySet().forEach(id->{
                    if(id == currentCartId)
                        System.out.printf("Cart #%d     **Current cart\n",id);
                    else
                        System.out.printf("Cart #%d\n",id);

                } );
            }
            else if(input.startsWith("cart info"))
            {
                if(currentCartId == -1)
                    System.out.println("No cart selected");
                else{
                    System.out.printf("Cart #%d content:\n",currentCartId);
                    carts.get(currentCartId).getContent()
                            .forEach(product -> System.out.printf("#%d %s %d\n",product.getId(),product.getName(),product.getPrice()));
                }
            }
            else if(input.startsWith("list products"))
            {
                System.out.println("Available products:");
                context.getBean(ProductRepository.class).loadAll()
                        .forEach(product -> System.out.printf("#%d %s %d\n",product.getId(),product.getName(),product.getPrice()));
            }
            else if(input.startsWith("cart add "))
            {
                if(currentCartId == -1){
                    System.out.println("No cart selected");
                    continue;
                }
                try{
                    int productId = getParam(input,9);
                    carts.get(currentCartId).addProduct(productId);
                }
                catch (NumberFormatException e){
                    System.out.println("CartId format exception!");
                }
            }
            else if(input.startsWith("cart remove "))
            {
                if(currentCartId == -1){
                    System.out.println("No cart selected");
                    continue;
                }
                try{
                    int productId = getParam(input,12);
                    carts.get(currentCartId).removeProduct(productId);
                }
                catch (NumberFormatException e){
                    System.out.println("CartId format exception!");
                }
            }
            else if(input.startsWith("quit"))
                break;
            else
                System.out.println("Command not recognized!");
        }
    }

    private static int getParam(String str, int prefixSize) throws NumberFormatException {
        return Integer.parseInt(str.substring(prefixSize));
    }


}
