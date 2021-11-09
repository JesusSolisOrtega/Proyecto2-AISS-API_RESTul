
package aiss.model;

import java.util.List;
import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Place {

    private Integer id;
    private String name;
    private String description;
    private String location;
    private Boolean outdoor;
    private Boolean disabledAccesibility;
    private List<Review> reviews = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Place() {
    }

    /**
     * 
     * @param outdoor
     * @param reviews
     * @param name
     * @param description
     * @param location
     * @param id
     * @param disabledAccesibility
     */
    public Place(Integer id, String name, String description, String location, Boolean outdoor, Boolean disabledAccesibility, List<Review> reviews) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.outdoor = outdoor;
        this.disabledAccesibility = disabledAccesibility;
        this.reviews = reviews;
    }
    
    public Place(String name, String description, String location, Boolean outdoor, Boolean disabledAccesibility,
			List<Review> reviews) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
		this.outdoor = outdoor;
		this.disabledAccesibility = disabledAccesibility;
		this.reviews = reviews;
	}
    

	public Place(String name, String description, String location, Boolean outdoor, Boolean disabledAccesibility) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
		this.outdoor = outdoor;
		this.disabledAccesibility = disabledAccesibility;
	}
	
	

	public Place(Integer id, String name, String description, String location, Boolean outdoor,
			Boolean disabledAccesibility) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.outdoor = outdoor;
		this.disabledAccesibility = disabledAccesibility;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getOutdoor() {
        return outdoor;
    }

    public void setOutdoor(Boolean outdoor) {
        this.outdoor = outdoor;
    }

    public Boolean getDisabledAccesibility() {
        return disabledAccesibility;
    }

    public void setDisabledAccesibility(Boolean disabledAccesibility) {
        this.disabledAccesibility = disabledAccesibility;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Place.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
        sb.append(',');
        sb.append("outdoor");
        sb.append('=');
        sb.append(((this.outdoor == null)?"<null>":this.outdoor));
        sb.append(',');
        sb.append("disabledAccesibility");
        sb.append('=');
        sb.append(((this.disabledAccesibility == null)?"<null>":this.disabledAccesibility));
        sb.append(',');
        sb.append("reviews");
        sb.append('=');
        sb.append(((this.reviews == null)?"<null>":this.reviews));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.outdoor == null)? 0 :this.outdoor.hashCode()));
        result = ((result* 31)+((this.reviews == null)? 0 :this.reviews.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.location == null)? 0 :this.location.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.disabledAccesibility == null)? 0 :this.disabledAccesibility.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Place) == false) {
            return false;
        }
        Place rhs = ((Place) other);
        return ((((((((this.outdoor == rhs.outdoor)||((this.outdoor!= null)&&this.outdoor.equals(rhs.outdoor)))&&((this.reviews == rhs.reviews)||((this.reviews!= null)&&this.reviews.equals(rhs.reviews))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.location == rhs.location)||((this.location!= null)&&this.location.equals(rhs.location))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.disabledAccesibility == rhs.disabledAccesibility)||((this.disabledAccesibility!= null)&&this.disabledAccesibility.equals(rhs.disabledAccesibility))));
    }

}
