<?php
namespace model;
require_once('User.php');
require_once(dirname(__FILE__,2) . '/exception/ModelNullException.php');
require_once(dirname(__FILE__,1) . '/Model.php');
class Review extends \model\Model{

    protected $title, $description, $rating, $username, $id, $review_date, $recipe_id;
    public function __construct(String $title = null, float $rating = null, User $username = null, Recipe $recipe_id = null, String $description = null, \DateTime $review_date = null, int $id = null){
        $this->title = $title;
        $this->description = $description;
        $this->rating = $rating;
        $this->id = $id;
        $this->username = $username;
        $this->recipe_id = $recipe_id;
        $this->review_date = $review_date;
    }
    //the title of the review
    public function getTitle() : String{
        if($this->title !== null){
            return $this->title;
        }
        throw new \exception\ModelNullException("Title value is null");
    }
    //the description of the review
    public function getDescription() : String{
        if($this->description !== null){
            return $this->description;
        }
        throw new \exception\ModelNullException("Description value is null");
    }
    //the rating of the review
    public function getRating() : Float{
        if($this->rating !== null){
            return $this->rating;
        }
        throw new \exception\ModelNullException("Rating value is null");
    }
    //the username of the person that wrote the review
    public function getUsername() : \model\User{
        if($this->username !== null){
            return $this->username;
        }
        throw new \exception\ModelNullException("Username value is null");
    }
    //the date the review was written
    public function getReviewDate() : \DateTime{
        if($this->review_date !== null){
          return $this->review_date;
        }
        throw new \exception\ModelNullException("Review date value is null");
    }
    //the id of the review
    public function getId() : int{
        if($this->id !== null){
            return $this->id;
        }
        throw new \exception\ModelNullException("id value is null");
    }
    //the id of the recipe the review is about
    public function getRecipeId() : \model\Recipe{
        if($this->recipe_id !== null){
            return $this->recipe_id;
        }
        throw new \exception\ModelNullException("Recipe value is null");
    }
    //the title of the review
    public function setTitle(String $title){
        $this->title = $title;
    }
    //the description of the review
    public function setDescription(String $description){
        $this->description = $description;
    }
    //the rating of the review
    public function setRating(Float $rating) {
        $this->rating = $rating;
    }
    //the username of the person that wrote the review
    public function setUsername(User $username) {
        $this->username = $username;
    }
    //the date the review was written
    public function setReviewDate(\DateTime $date) {
        $this->reviewDate = $reviewDate;
    }
    //the id of the review
    public function setId(int $id){
        $this->id = $id;
    }
    //the id of the recipe the review is about
    public function setRecipeId(Recipe $recipeId){
        $this->recipe_id = $recipeId;
    }
    //get all columns of this entity
    public function getVariables(){
        return [['title'], ['description'], ['rating'], ['id'], ['username'], ['review_date'], ['recipe_id']];
    }
    //return the result of this object to the UI
    public function jsonSerialize() {
        $vars = get_object_vars($this);
        $vars["recipe_id"] = ($this->recipe_id !== null ? $this->recipe_id->getId() : null);
        $vars["review_date"] = ($this->review_date !== null ? $this->review_date->format('d-m-Y') : null);
        $vars["username"] = ($this->username !== null ? $this->username->getUsername() : null);
        $json = parent::setUpJson($vars);
        return $json;
    }
}

class ReviewPDO extends Review{
    public function __construct(String $title = null, float $rating = null, String $username = null, int $recipe_id = null, String $description = null, String $review_date = null, int $id = null){
      $this->title = ($title !== null ? $title : $this->title);
      $this->description = ($description !== null ? $description : $this->description);
      $this->rating = ($rating !== null ? $rating : $this->rating);
      $this->id = ($id !== null ? $id : $this->id);
      $this->username = ($username !== null  ? $username : $this->username);
      $this->recipe_id = ($recipe_id !== null ? $recipe_id : $this->recipe_id);
      $this->review_date = ($review_date !== null ? $review_date : $this->review_date);
      $this->review_date = ($this->review_date != null && gettype($this->review_date) != "object"? \DateTime::createFromFormat('Y-m-d', $this->review_date) : $this->review_date);
      $this->username = ($this->username !== null && gettype($this->username) != "object" ? new \model\User($this->username) : $this->username);
      $this->recipe_id = ($this->recipe_id !== null && gettype($this->recipe_id) != "object" ? new \model\Recipe($this->recipe_id) : $this->recipe_id);
    }
}
?>
