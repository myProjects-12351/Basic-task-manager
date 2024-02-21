public class Tasks {
    private final String title;
    private final String description;
    private final String date;
    private boolean isFinished;

    public Tasks(String title, String description, String date, boolean isFinished) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.isFinished = isFinished;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(){
        isFinished = !isFinished;
    }

    public void print(){
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Date: " + date);
        System.out.println("Is finished: " + isFinished);
    }
}
