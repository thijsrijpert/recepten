<?php
namespace model;
require_once('User.php');
class Review{

    private $title, $description, $rating, $username, $id, $review_date, $recipeId;
    public function __construct(String $title, float $rating, User $username, Recipe $recipeId, String $description = null){
        $this->title = $title;
        $this->description = $description;
        $this->rating = $rating;
        $this->username = $username;
        $this->id = $id;
        $this->recipeId = $recipeId;
    }

    public function getTitle() : String{
        return $this->title;
    }

    public function getDescription() : ?String{
        return $this->description;
    }

    public function getRating() : Float{
        return $this->rating;
    }

    public function getUsername() : User{
        return $this->username;
    }

    public function getDate() : \DateTime{
        return $this->date;
    }

    public function getId() : int{
        return $this->id;
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

    public function setDate(\DateTime $date) {
        $this->date = $date;
    }

    public function setId(int $id){
        $this->id = $id;
    }

    public function setRecipeId(int $recipeId){
        $this->recipeId = $recipeId;
    }

    public function getRecipeId() : Recipe{
        return $this->recipeId;
    }

}
?>
