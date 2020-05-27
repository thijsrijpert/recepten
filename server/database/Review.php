<?php
namespace database;
  require_once(dirname(__FILE__,1) . '/Database.php');
  require_once(dirname(__FILE__,2) . '/model/Review.php');
  require_once(dirname(__FILE__,2) . '/exception/ModelNullException.php');
  require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
  require_once(dirname(__FILE__,1) . '/CRInterface.php');
  require_once(dirname(__FILE__,1) . '/CRUD.php');


  class Review extends CRUD implements CRInterface{

      function __construct(QueryBuilder $query = null){
          $sql = "INSERT INTO Review (title, description, rating, username, recipe_id) VALUES (:title, :description, :rating, :username, :recipe_id)";
          $this->stmt = \database\Database::getConnection()->prepare($sql);

          parent::__construct($query);
      }

      function insert(\model\Model $model) : string{
          try{
              $description = $model->getDescription();
          }catch(\exception\ModelNullException $e){
              $description = null;
          }
          try{
            $title = $model->getTitle();
            $rating = $model->getRating();
            $username = $model->getUsername()->getUsername();
            $recipeId = $model->getRecipeId()->getId();
          }catch(\exception\ModelNullException $e){
              throw new \exception\NullPointerException($e->getMessage());
          }

          $this->stmt->bindParam(':title', $title);
          $this->stmt->bindParam(':description', $description);
          $this->stmt->bindParam(':rating', $rating);
          $this->stmt->bindParam(':username', $rating);
          $this->stmt->bindParam(':recipe_id', $recipeId);

          $this->stmt->execute();

          return $this->stmt->errorCode();
      }

      function select(\model\Model $model) : array{
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
            $this->select[0]->bindParam(':review_date', $model->getReviewDate()->format('Y-m-d'));
        }catch(\exception\ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':rating', $model->getRating());
        }catch(\exception\ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':username', $model->getUsername()->getUsername());
        }catch(\exception\ModelNullException $e){}

        $this->select[0]->execute();

        $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS, 'model\ReviewPDO');

        return array($this->select[0]->errorCode(), array($results));
      }

      function error_handler($errno, $errstr, $errfile, $errline){

      }
  }
?>
