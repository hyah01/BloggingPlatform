package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        System.out.println("\n--== Currently Deleting Comments ==--");
        System.out.println("Enter the post title that you want to delete");
        Blog blog = blogManager.viewPost(scanner.nextLine());
        if (blog != null){
            System.out.println("Are you sure you want to delete this post? all the comments will be deleted also. ( Y / N )");
            System.out.println(blog);
            String comfirmation = scanner.nextLine();
            if (comfirmation.equals("Y")){
                blogManager.deletePostID(blog.getId());
                commentManager.deleteComments(blog.getId());
            }
        }

    }

    private static void editPost() {
        System.out.println("\n--== Currently Editing Comments ==--");
        System.out.println("Enter the post title that you want to edit");
        Blog blog = blogManager.viewPost(scanner.nextLine());
        if (blog != null){
            String title = blog.getTitle();
            String content = blog.getContent();
            List<String> tags = blog.getTags();
            String category = blog.getCategory();
            int input;
            boolean isNotDone = true;

            while (isNotDone){
                System.out.println("""
                        Which part of the post do you want to edit?
                        1) Title
                        2) Content
                        3) Tags
                        4) Category
                        5) Submit
                        """);
                try {
                    input = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e){
                    System.out.println("Try 1-5");
                    throw new RuntimeException(e);
                }

                switch (input){
                    case 1:
                        System.out.println("Enter Post new Title:");
                        title = scanner.nextLine();
                        break;
                    case 2:
                        System.out.println("Enter Post new Content");
                        content = scanner.nextLine();
                        break;
                    case 3:
                        System.out.println("Enter one or multiple new tags");
                        tags = Arrays.asList(scanner.nextLine().split(" "));
                    case 4:
                        System.out.println("Enter Post new category");
                        category = scanner.nextLine();
                    case 5:
                        blogManager.editPost(blog.getTitle(),title,content,tags,category);
                        isNotDone = false;
                        break;
                    default:
                        System.out.println("Try 1-5");
                }
                System.out.println("Successfully edited the post");
            }
        }
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
            boolean isTrue = true;
            while (isTrue){
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
                            System.out.println(blog);
                            viewComments(blog);
                            break;
                        case 2:
                            addComents(blog);
                            break;
                        case 3:
                            DeleteThisPost(blog);
                            isTrue = false;
                            break;
                        case 4:
                            System.out.println("Exiting...");
                            isTrue = false;
                            break;
                        default:
                            System.out.println("Invalid, Try 1-4");
                    }
                } catch (NumberFormatException e){
                    System.out.println("Invalid, Try number 1-4");
                }
            }
        }
    }

    private static void DeleteThisPost(Blog blog) {
        System.out.println("\n--== Currently Deleting Comments ==--");
        System.out.println("Are you sure you want to delete this post? all the comments will be deleted also. ( Y / N )");
        String input = scanner.nextLine();
        if (input.equals("Y")){
            blogManager.deletePostID(blog.getId());
            commentManager.deleteComments(blog.getId());
        }

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
