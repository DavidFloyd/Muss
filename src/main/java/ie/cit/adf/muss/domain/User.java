package ie.cit.adf.muss.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;

@Entity
public class User {

	// --------------------------- Values ----------------------------
	
	// ------------------------- Attributes --------------------------
	
	@Id
	@GeneratedValue
	private int id;
	private String username;
	private String password;
	@Email
	private String email;
	private String name;
	@Lob
	private byte[] picture;
	private int points;
	
	// -------------------------- Relations --------------------------
	

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Collection<Tag> tags;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Collection<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "review_likes",
    		uniqueConstraints = {
            		@UniqueConstraint(name = "user_review_unique", columnNames = {"user_id", "review_id"})
            },
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "review_id", referencedColumnName = "id")
            }
    )
    @Fetch(FetchMode.SELECT)
    private Collection<Review> reviewLikes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "chobject_likes",
            uniqueConstraints = {
            		@UniqueConstraint(name = "user_object_unique", columnNames = {"user_id", "chobject_id"})
            },
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "chobject_id", referencedColumnName = "id")
            }
    )
    @Fetch(FetchMode.SELECT)
    private Collection<ChObject> chObjectLikes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_following",
            joinColumns = {
                    @JoinColumn(name = "follower_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "following_id", referencedColumnName = "id")
            }
    )
    private Collection<User> followed;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "followed")
    private Collection<User> followers;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_badges",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "badge_id", referencedColumnName = "id")
            }
    )
    @Fetch(FetchMode.SELECT)
    private List<Badge> badges;
    
    // ------------------------- Constructor -------------------------
    
    public User() {
    }

    public User(int id, String username, String password, String email, String name, byte[] picture, int points,
                Collection<Tag> tags) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.points = points;
        this.tags = tags;
    }

    // ---------------------- Getters & Setters ----------------------
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    public Collection<Review> getReviewLikes() {
        return reviewLikes;
    }

    public void setReviewLikes(Collection<Review> reviewLikes) {
        this.reviewLikes = reviewLikes;
    }

    public Collection<ChObject> getChObjectLikes() {
        return chObjectLikes;
    }

    public void setChObjectLikes(Collection<ChObject> chObjectLikes) {
        this.chObjectLikes = chObjectLikes;
    }

    public Collection<User> getFollowed() {
        return followed;
    }

    public void setFollowed(Collection<User> followed) {
        this.followed = followed;
    }

    public Collection<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Collection<User> followers) {
        this.followers = followers;
    }
    
    public List<Badge> getBadges() {
 		return badges;
 	}

 	public void setBadges(List<Badge> badges) {
 		this.badges = badges;
 	}

    // ------------------------ Quick methods ------------------------
    
    public boolean beingFollowed(User principal) {
        return followers.contains(principal);
    }

    public boolean isPrincipal(User principal) {
        return this.id == principal.getId();
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof User && id > 0 && id == ((User) obj).getId();
    }

    @Override
    public int hashCode() {
        return id * username.hashCode() * email.hashCode();
    }

}
