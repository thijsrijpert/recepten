<?php
namespace database;
  require_once(dirname(__FILE__,1) . '/Database.php');
  require_once(dirname(__FILE__,2) . '/model/Review.php');

  class Review {

      private $stmt;

      function __construct(){
          $sql = "INSERT INTO Religion (title, description, rating, username, review_date) VALUES (:title, :description, :rating, :username, :review_date)";
          $this->stmt = \database\Database::getConnection()->prepare($sql);
      }

      function insert($model) {
          $title = $model->getTitle();
          $description = $model->getDescription();
          $rating = $model->getRating();
          $username = $model->getUsername()->getUsername();
          //going to replace this with PL/SQL
          $dt = new \DateTime();
          $review_date = $dt->format('Y-m-d');

          $this->stmt->bindParam(':title', $title);
          $this->stmt->bindParam(':description', $description);
          $this->stmt->bindParam(':rating', $rating);
          $this->stmt->bindParam(':username', $rating);
          $this->stmt->bindParam(':review_date', $review_date);

          $this->stmt->execute();

          return $this->stmt->errorCode();
      }
  }
?>
