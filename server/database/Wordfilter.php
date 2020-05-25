<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,1) . '/CRUD.php');
require_once(dirname(__FILE__,2) . '/model/Wordfilter.php');
  class Wordfilter extends CRUD{

    function __construct(QueryBuilder $query = null){
          $sql = "INSERT INTO Wordfilter (word) VALUES (:word)";
          $this->stmt = \database\Database::getConnection()->prepare($sql);

          parent::__construct($query);
    }

    function insert(\model\Model $model) : String{
        $word = $model->getWord();
        $this->stmt->bindParam(':word', $word);
        $this->stmt->execute();

        return $this->stmt->errorCode();
    }

    function select(\model\Model $model) : array{
      
        try{
            $this->select[0]->bindParam(':word', $model->getWord());
        }catch(\exception\ModelNullException $e){}

        $this->select[0]->execute();

        $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\Wordfilter');

        return array($this->select[0]->errorCode(), array($results));
    }

    function error_handler($errno, $errstr, $errfile, $errline){

    }
  }
  ?>
