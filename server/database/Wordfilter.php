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

    function select(\model\Model $model) : array{
        if(null != $model->getWord()){
            $this->select[0]->bindParam(':name', $model->getName());
        }

        $this->select[0]->execute();

        $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\Wordfilter');

        return array($this->select[0]->errorCode(), array($results));
    }

  }

 ?>
