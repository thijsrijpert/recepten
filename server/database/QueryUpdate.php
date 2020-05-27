<?php
namespace database;
require_once(dirname(__FILE__,1) . '/QueryParent.php');
class QueryUpdate extends QueryParent{

    private $setArguments;
    public function __construct(\model\Model $entity){
        $this->entity = $entity;
    }

    public function setSetArguments(array $arguments = array()) : bool{
        $approvedArguments = $this->entity->getUpdateVariables();
        $this->setArguments = array();

        if(count($arguments) != 0 && count($arguments) <= count($approvedArguments)){
            for($i = 0; $i <= count($arguments) - 1; $i++){
                for($k = 0; $k <= count($approvedArguments) - 1; $k++){
                    if(count($arguments[$i]) == 2){
                        if($arguments[$i][0] == $approvedArguments[$k][0]){
                            $this->setArguments[$i][0] = $approvedArguments[$k][0];
                        }
                    }else{
                        return false;
                    }
                }

            }
        }

        if(count($arguments) === count($this->setArguments)){
            return true;
        }
        $this->setArguments = array();
        return false;
    }



    /**
     * Get the value of Set Arguments
     *
     * @return array
     */
    public function getSetArguments() : array
    {
        return $this->setArguments;
    }


}
