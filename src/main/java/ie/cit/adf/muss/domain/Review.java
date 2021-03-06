package ie.cit.adf.muss.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Review {

	@Id
	@GeneratedValue
	private int id;
	private String title;
	private Date date;
	private Integer rating;

	@Column(columnDefinition = "TEXT")
	private String content;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="chobject_id")
	private ChObject chObject;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="reviewLikes", cascade=CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Collection<User> likes;

	public Review() {
		this.likes = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(int rating) {
		if (rating < 1  ||  rating > 5)
			throw new IllegalArgumentException();
		this.rating = rating;
	}

	public ChObject getChObject() {
		return chObject;
	}

	public void setChObject(ChObject chObject) {
		this.chObject = chObject;
		if (!chObject.getReviews().contains(this)) {
			chObject.getReviews().add(this);
		}
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<User> getLikes() {
		return likes;
	}

	public void setLikes(Collection<User> likes) {
		this.likes = likes;
		likes.forEach(user -> {
			if (!user.getReviewLikes().contains(this))
				user.getReviewLikes().add(this);
		});
	}

	public boolean addLike(User user) {
		if (!this.likes.contains(user)) {
			this.likes.add(user);
			if (!user.getReviewLikes().contains(this))
				return user.getReviewLikes().add(this);
		}
		return false;
	}

	public boolean removeLike(User user) {
		user.getReviewLikes().remove(this);
		return this.likes.remove(user);
	}

}
