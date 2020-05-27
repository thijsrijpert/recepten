<?php
namespace database;
require_once(dirname(__FILE__, 2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__, 1) . '/QueryBuilderParent.php');
class QueryBuilderUpdate extends QueryBuilderParent{

    public function __construct(QueryUpdate $query){
        $this->query = $query;
    }

    public function generateSql() : bool{
        $this->generateUpdate();
        if($this->generateSet() && parent::generateWhere()){
            return true;
        }else{
            $this->sql = "";
            return false;
        }
    }

    public function generateSet() : bool{
        $setArguments = $this->query->getSetArguments();
        var_dump($setArguments);
        if(count($setArguments) > 0){
            $this->sql .= " SET " . $setArguments[0][0] . " = " . ":" . $setArguments[0][0] . "Update";

            if(count($setArguments) > 1){
                for($i = 1; $i <= count($setArguments) - 1; $i++){
                    $this->sql .= ", " . $setArguments[$i][0] . " = " . ":" . $setArguments[$i][0] . "Update";
                }
            }
        }else{
            echo 'Hallotjes';
            return false;
        }
        echo 'WHYYYYYYYYYYYYYYYY';
        return true;
    }

    public function generateUpdate(){
        $this->sql .= "UPDATE " . substr(get_class($this->query->getEntity()), 6);
    }

}
?>
