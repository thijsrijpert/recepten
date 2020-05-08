<?php
namespace database;
  require_once(dirname(__FILE__,1) . '/Database.php');
  require_once(dirname(__FILE__,2) . '/model/Review.php');

  class Review extends CRUD implements CRUDInterface{

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

      function select($model){
        try{
            $this->select[0]->bindParam(':title', $model->getTitle());
        }catch(\exception\ModelNullException $e){}
        try{
            $this->select[0]->bindParam(':description', $model->getId());
        }catch(\exception\ModelNullException $e){}
        try{
            $this->select[0]->bindParam(':id', $model->getId());
        }catch(\exception\ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':recipe_id', $model->getRecipeId());
        }catch(\exception\ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':review_date', $model->getReviewDate());
        }catch(\exception\ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':rating', $model->getRating());
        }catch(\exception\ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':username', $model->get());
        }catch(\exception\ModelNullException $e){}

        $this->select[0]->execute();

        $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\Review');

        return array($this->select[0]->errorCode(), array($results));
      }
  }
?>
