<?php
namespace database;
class CRUD {
    protected $select = array();
    protected $stmt;
    protected $update = array();
    protected $delete;

    public function __construct($query = null){
        if(isset($query)){
            $this->addSelectStatement($query);
        }

        set_error_handler(array($this, 'error_handler'));
    }

    public function addSelectStatement($query){
        if(gettype($query) ===  'array'){
            foreach($query as $value){
                $this->addSelectStatement($value);
            }
        }else{
            $this->assignStatement($query);
        }
    }

    public function assignStatement(QueryBuilderParent $query){
        if($query->getSql() != null || $query->generateSql()){
            //first condition serves for unit testing with mock objects, second condition for prod enviorment
            if(substr(substr(get_class($query), 4), 1,  -9) === 'QueryBuilder' || get_class($query) === 'database\QueryBuilder'){
                $this->select[] = Database::getConnection()->prepare($query->getSql());
            }else{
                $this->update[] = Database::getConnection()->prepare($query->getSql());
            }
        }
    }
}
