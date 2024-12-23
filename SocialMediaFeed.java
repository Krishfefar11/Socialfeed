import java.util.*;

class Post {
    private String content;
    private String author;
    private Date timestamp;

    public Post(String author, String content) {
        this.author = author;
        this.content = content;
        this.timestamp = new Date();
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + author + ": " + content;
    }
}

public class SocialMediaFeed {
    private Map<String, List<Post>> userPosts;
    private Queue<Post> feed;
    private final int FEED_SIZE = 10; // Maximum posts displayed in the feed

    public SocialMediaFeed() {
        userPosts = new HashMap<>();
        feed = new LinkedList<>();
    }

    public void addUser(String username) {
        if (!userPosts.containsKey(username)) {
            userPosts.put(username, new ArrayList<>());
            System.out.println("User " + username + " added.");
        } else {
            System.out.println("User already exists.");
        }
    }

    public void addPost(String username, String content) {
        if (!userPosts.containsKey(username)) {
            System.out.println("User does not exist. Please add the user first.");
            return;
        }

        Post newPost = new Post(username, content);
        userPosts.get(username).add(newPost);
        feed.add(newPost);

        if (feed.size() > FEED_SIZE) {
            feed.poll(); // Remove the oldest post to maintain the feed size
        }

        System.out.println("Post added successfully by " + username + ".");
    }

    public void viewFeed() {
        System.out.println("\n--- Social Media Feed ---");
        List<Post> feedList = new ArrayList<>(feed);
        feedList.sort((p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp())); // Sort by newest first

        for (Post post : feedList) {
            System.out.println(post);
        }
    }

    public void viewUserPosts(String username) {
        if (!userPosts.containsKey(username)) {
            System.out.println("User does not exist.");
            return;
        }

        System.out.println("\n--- Posts by " + username + " ---");
        List<Post> posts = userPosts.get(username);

        if (posts.isEmpty()) {
            System.out.println("No posts available.");
        } else {
            for (Post post : posts) {
                System.out.println(post);
            }
        }
    }

    public static void main(String[] args) {
        SocialMediaFeed smf = new SocialMediaFeed();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Social Media Simulation ---");
            System.out.println("1. Add User");
            System.out.println("2. Add Post");
            System.out.println("3. View Feed");
            System.out.println("4. View User Posts");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    smf.addUser(username);
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String user = scanner.nextLine();
                    System.out.print("Enter content: ");
                    String content = scanner.nextLine();
                    smf.addPost(user, content);
                    break;

                case 3:
                    smf.viewFeed();
                    break;

                case 4:
                    System.out.print("Enter username: ");
                    String userToView = scanner.nextLine();
                    smf.viewUserPosts(userToView);
                    break;

                case 5:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
