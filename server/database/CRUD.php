<?php
namespace database;
class CRUD {
    protected $select = array();
    protected $stmt;
    protected $update;
    protected $delete;

    public function __construct(QueryBuilder $query = null){
        if(isset($query)){
          var_dump($query);
            $this->addSelectStatement($query);
        }

        set_error_handler(array($this, 'error_handler'));
    }

    public function addSelectStatement(QueryBuilder $query){
        if($query->getSql() != null || $query->generateSql()){
            $this->select[] = Database::getConnection()->prepare($query->getSql());
        }
    }
}
