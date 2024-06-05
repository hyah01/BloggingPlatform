package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BlogSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DBConnection dbConnection = new DBConnection();
    private static final CommentManager commentManager = new CommentManager("Comment");
    private static final BlogManager blogManager = new BlogManager("Blog");

    public static void main(String[] args) {
        menu();
    }

    private static void menu(){
        while(true){
            try {
                while (true){
                    System.out.println("""

                    1) View all Posts
                    2) View Post ( View Post To Comment )
                    3) Add Post
                    4) Edit Post
                    5) Delete Post
                    6) Exit
                    """);

                    int input = Integer.parseInt(scanner.nextLine());
                    switch (input){
                        case 1 -> viewAllPosts();
                        case 2 -> viewPost();
                        case 3 -> addPost();
                        case 4 -> editPost();
                        case 5 -> deletePost();
                        case 6 -> System.exit(0);
                        default -> System.out.println("Invalid, Try 1-6");
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid, Try number 1-9");
            }
        }
    }

    private static void deletePost() {
    }

    private static void editPost() {
    }

    private static void addPost() {
        System.out.println("\n--== Currently Adding a Post ==--");
        System.out.println("Enter Post Title:");
        String title = scanner.nextLine();
        System.out.println("Enter Post Content");
        String content = scanner.nextLine();
        System.out.println("Enter one or multiple tags");
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(scanner.nextLine().split(" ")));
        System.out.println("Enter Post category");
        String category = scanner.nextLine();
        blogManager.addPost(title,content,tags,category);
        System.out.println("Post added successfully");
    }

    private static void viewPost() {
        System.out.println("\n--== Currently Viewing Post ==--");
        System.out.println("Enter Post Title to view Post:");
        Blog blog = blogManager.viewPost(scanner.nextLine());
        if (blog != null){
            System.out.println(blog);
            try {
                int input;
                boolean isValid;
                do {
                    System.out.println("""

                    1) View Comments
                    2) Add Comments
                    3) Delete this Post
                    4) Exit
                    """);

                    input = Integer.parseInt(scanner.nextLine());
                    isValid = input >= 1 && input <= 4;
                    if (!isValid){
                        System.out.println("Invalid, Try number 1-4");
                    }
                } while (!isValid);
                switch (input){
                    case 1:
                        viewComments(blog);
                        break;
                    case 2:
                        addComents(blog);
                        break;
                    case 3:
                        DeleteThisPost(blog);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid, Try 1-4");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid, Try number 1-4");
            }
        }
    }

    private static void DeleteThisPost(Blog blog) {

    }

    private static void addComents(Blog blog) {
        System.out.println("\n--== Currently Adding Comments ==--");
        System.out.println("For: " + blog.getTitle());
        System.out.println("What's the comment?");
        String comment = scanner.nextLine();
        System.out.println("Who's the author?");
        String author = scanner.nextLine();
        commentManager.addComment(blog.getId(),comment,author);
        System.out.println("Comment added successfully");
    }

    private static void viewComments(Blog blog) {
        System.out.println("\n--== Currently Viewing Comments ==--");
        System.out.println("For: " + blog.getTitle());
        ArrayList<Comment> comments = commentManager.getCommentsForPost(blog.getId());
        for (Comment comment: comments){
            System.out.println(comment);
        }
    }

    private static void viewAllPosts() {
        ArrayList<Blog> blogs = blogManager.getPosts();
        System.out.println("\n--== Currently Viewing Posts ==--");
        for (Blog blog : blogs){
            System.out.println(blog);
        }
    }

}
