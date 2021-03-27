package RedBox;


// class to store a movie in
public class Movie implements Comparable<Movie> {


    private String title;
    private int available;
    private int rented;

    public Movie()
    {

    }

    // overloaded constructor
    public Movie(String title, int available)
    {
        this.title = title;
        this.available = available;
        this.rented = 0;
    }

    // create a ToString method that will format the movie however i want it
    public String toString()
    {
        String movieString = String.format("%-33s %-20s %-7s",title, available, rented);
        return movieString;
    }

    // create a function to increase the available number of movies
    public void addAvailable(int amount)
    {
        available = available + amount;
    }

    // create a function to decrease the available number of movies
    public void removeAvailable(int amount)
    {
        if (available < amount)
        {
            available = 0;
        } else
        {
            available = available - amount;
        }
    }

    // TODO: create a function to rent a copy to change the amount rented and amount available
    public boolean rentTitle(int amountToRent)
    {
        // check if amount available is less than amount we want to rent
        if (available < amountToRent)
        {
            return false;
        }
        rented = rented + amountToRent;
        available = available - amountToRent;

        return true;
    }

    // TODO: create a function to return a copy
    public boolean returnTitle(int amountToReturn)
    {
        // check if amount rented is less than the amount to return
        rented = rented - Integer.min(rented, amountToReturn);

        available = available + amountToReturn;
        return true;
    }
    public String getTitle()
    {
        return title;
    }

    public int getAvailable()
    {
        return available;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setAvailable(int available)
    {
        this.available = available;
    }

    public void setRented(int rented) { this.rented = rented; }

    public int getRented() { return rented; }

    @Override
    public int compareTo(Movie movie) {
        return this.getTitle().compareTo(movie.getTitle());

        // should return negative # if less than, positive if greater than, 0 if equal
    }
}
