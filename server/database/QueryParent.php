<?php
namespace database;
abstract class QueryParent{
  protected $whereArguments = array();
  protected $entity;

  public function setWhereArguments(array $arguments = array()) : bool{
    $approvedArguments = $this->entity->getVariables();
    $this->whereArguments = array();
    $skipper = false;
    if(count($arguments) != 0 && count($arguments) <= count($approvedArguments)){
        for($i = 0; $i <= count($arguments) - 1; $i++){
            for($k = 0; $k <= count($approvedArguments) - 1; $k++){

                if(count($arguments[$i]) == 3){
                    if($arguments[$i][0] == $approvedArguments[$k][0]){
                        $this->whereArguments[$i][0] = $approvedArguments[$k][0];
                        $this->whereArguments[$i][1] = $this->getComparisonOperator($arguments[$i][1]);
                        if($arguments[$i][0] == 'password'){
                            unset($this->whereArguments[$i]);
                            $skipper = true;
                        }
                    }
                }else{
                    return false;
                }

            }
        }
    }
    //\var_dump($this->whereArguments);
    if(($skipper && count($arguments) - 1 === count($this->whereArguments)) || count($arguments) === count($this->whereArguments)){

        return true;
    }
    $this->whereArguments = array();
    return false;
  }

  public function getComparisonOperator($operator) : String{
       if($operator == ALLOWED_COMPARISON['<']){
           return '<';
       }else if($operator == ALLOWED_COMPARISON['=']){
           return '=';
       }else if($operator == ALLOWED_COMPARISON['>']){
           return '>';
       }
       throw new \exception\NullPointerException("The request send had an illigal operator");
   }

  /**
   * Get the value of Where Arguments
   *
   * @return mixed
   */
  public function getWhereArguments()
  {
      return $this->whereArguments;
  }

  public function getEntity() : \model\Model{
      return $this->entity;
  }


}
?>
