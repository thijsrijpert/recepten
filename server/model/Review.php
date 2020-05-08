<?php
namespace model;
require_once('User.php');
class Review{

    private $title, $description, $rating, $username, $id, $reviewDate, $recipeId;
    public function __construct(String $title = null, float $rating = null, User $username = null, Recipe $recipeId = null, String $description = null){
        $this->title = $title;
        $this->description = $description;
        $this->rating = $rating;
        $this->username = $username;
        $this->id = $id;
        $this->recipeId = $recipeId;
    }

    public function getTitle() : String{
        if($this->title != null){
            return $this->title;
        }
        throw new \exception\ModelNullException("Title value is null");
    }

    public function getDescription() : String{
        if($this->description != null){
            return $this->description;
        }
        throw new \exception\ModelNullException("Description value is null");
    }

    public function getRating() : Float{
        if($this->rating != null){
            return $this->rating;
        }
        throw new \exception\ModelNullException("Rating value is null");
    }

    public function getUsername() : User{
        if($this->username != null){
            return $this->username;
        }
        throw new \exception\ModelNullException("Username value is null");
    }

    public function getReviewDate() : \DateTime{
        if($this->reviewDate != null){
          return $this->date;
        }
        throw new \exception\ModelNullException("Review date value is null");
    }

    public function getId() : int{
        if($this->id != null){
            return $this->id;
        }
        throw new \exception\ModelNullException("id value is null");
    }

    public function getRecipeId() : Recipe{
        if($this->recipeId != null){
            return $this->recipeId;
        }
        throw new \exception\ModelNullException("Recipe value is null");
    }

    public function setTitle(String $title){
        $this->title = $title;
    }

    public function setDescription(?String $description){
        $this->description = $description;
    }

    public function setRating(Float $rating) {
        $this->rating = $rating;
    }

    public function setUsername(User $username) {
        $this->username = $username;
    }

    public function setReviewDateDate(\DateTime $date) {
        $this->reviewDate = $reviewDate;
    }

    public function setId(int $id){
        $this->id = $id;
    }

    public function setRecipeId(int $recipeId){
        $this->recipeId = $recipeId;
    }

    public function getVariables(){
        return [['title'], ['description'], ['rating'], ['id'], ['username'], ['review_date'], ['recipe_id'], ];
    }

    public function jsonSerialize() {
        $vars = get_object_vars($this);
        $json = $parent::setUpJson($vars);
        return $json;
    }

}
?>
