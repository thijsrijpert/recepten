<?php
namespace database;
require_once(dirname(__FILE__, 2) . '/exception/NullPointerException.php');
class QueryBuilder{

    private $sql;
    private $query;

    public function __construct(Query $query){
        $this->query = $query;
    }

    public function generateSql() : bool{

        $selectArguments = $this->query->getSelectArguments();
        $whereArguments = $this->query->getWhereArguments();
        $whereArguments = $this->query->getOrderArguments();

        if($selectArguments > 0){
            $this->sql = "SELECT " . $selectArguments[0][0];

            if(count($selectArguments) > 1){
                foreach($selectArguments as $value){
                    $this->sql .= ", " . $value[0];
                }
            }

            $this->sql .= " FROM " . substr(get_class($this->query->getEntity()), 6);
            if(count($whereArguments) > 0){
                $this->sql .= " WHERE ";
                foreach($whereArguments as $value){
                    $this->sql .= $value[0] . ' ' . $value[1] . ' :' . $value[0];
                }
            }

            if(count($orderArguments) > 0){
                $this->sql .= " ORDER BY ";
                foreach($orderArguments as $value){
                    $this->sql .= $value[0] . ' ' . $value[1];
                }
            }
        } else {
            return false;
        }

        return true;
    }

    public function getSql() : String{
        return $this->sql;
    }
}
?>
