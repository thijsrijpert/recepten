<?php
class Query{

    private $sql;
    private $selectArguments[];
    private $whereArguments[][];
    private $orderArguments[];
    private $groupArguments[];
    private $havingArguments[];
    private $entity;

    public function __construct(Model $entity){
        $this->entity = $entity;
    }

    public function setSelectArguments(array $arguments = array()) : boolean{
        $approvedArguments = $this->entity->getVariables();
        $this->selectArguments = array();
        if(count($arguments) == 0){
          $this->selectArguments[0] = '*';
        } else if(count($arguments) <= count($approvedArguments)){
            for($i = 0; $i <= count($arguments) - 1; $i++){
                for($k = 0; $k <= count($approvedArguments) - 1; $k++){
                    if($arguments[$i][0] == $approvedArguments[$k]){
                        $this->selectArguments[$i][0] = $approvedArguments[$k];
                    }
                }
            }
        }

        if($arguments === $this->selectArguments && $this->selectArguments != array()){
            return true;
        }

        return false;
    }

    public function setWhereArguments(array $arguments = array()) : boolean{
      $approvedArguments = $this->entity->getVariables();
      $this->whereArguments = array();

      if(count($arguments) != 0 && count($arguments) <= count($approvedArguments)){
          for($i = 0; $i <= count($arguments) - 1; $i++){
              for($k = 0; $k <= count($approvedArguments) - 1; $k++){
                  if($arguments[$i][0] == $approvedArguments[$k]){
                      $this->whereArguments[$i][0] = $approvedArguments[$k];
                      if(comparisonIsAllowed($arguments[$i][2])){
                          $this->whereArguments
                      }
                  }
              }
          }
      }

      if(count($arguments) === count($this->whereArguments)){
          return true;
      }

      return false;
    }

    private function getComparisonOperator($operator) : boolean{
        if($operator == ALLOWED_COMPARISON[0] ||  || ){
            return true;
        }else if($operator == ALLOWED_COMPARISON[1]){

        }else if($operator == ALLOWED_COMPARISON[2]){
            return 
        }
        return false;
    }
}
?>
