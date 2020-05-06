<?php
namespace database;
  require_once(dirname(__FILE__,1) . '/Database.php');
  require_once(dirname(__FILE__,2) . '/model/Review.php');

  class Review {

      private $stmt;

      function __construct(){
          $sql = "INSERT INTO Review (title, description, rating, username, recipe_id) VALUES (:title, :description, :rating, :username, :recipe_id)";
          $this->stmt = \database\Database::getConnection()->prepare($sql);
      }

      function insert($model) {
          $title = $model->getTitle();
          $description = $model->getDescription();
          $rating = $model->getRating();
          $username = $model->getUsername()->getUsername();
          $recipeId = $model->getRecipeId()->getId();

          $this->stmt->bindParam(':title', $title);
          $this->stmt->bindParam(':description', $description);
          $this->stmt->bindParam(':rating', $rating);
          $this->stmt->bindParam(':username', $rating);
          $this->stmt->bindParam(':recipe_id', $recipeId);

          $this->stmt->execute();

          return $this->stmt->errorCode();
      }
  }
?>
