<?php
class Query{

    private $sql;
    private $selectArguments = array();
    private $whereArguments;
    private $orderArguments;
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
      $this->orderArguments = array();

      if(count($arguments) != 0 && count($arguments) <= count($approvedArguments)){
          for($i = 0; $i <= count($arguments) - 1; $i++){
              for($k = 0; $k <= count($approvedArguments) - 1; $k++){
                  if($arguments[$i][0] == $approvedArguments[$k]){
                      $this->whereArguments[$i][0] = $approvedArguments[$k];
                      $this->whereArguments[$i][1] = getComparisonOperator($arguments[$i][1]);
                  }
              }
          }
      }

      if(count($arguments) === count($this->whereArguments)){
          return true;
      }

      return false;
    }

    public function setOrderArguments(array $arguments = array()) : boolean{
      $approvedArguments = $this->entity->getVariables();
      $this->whereArguments = array();

      if(count($arguments) != 0 && count($arguments) <= count($approvedArguments)){
          for($i = 0; $i <= count($arguments) - 1; $i++){
              for($k = 0; $k <= count($approvedArguments) - 1; $k++){
                  if($arguments[$i][0] == $approvedArguments[$k]){
                      $this->whereArguments[$i][0] = $approvedArguments[$k];
                      $this->whereArguments[$i][1] = getOrderOperator($arguments[$i][1]);
                  }
              }
          }
      }

      if(count($arguments) === count($this->whereArguments)){
          return true;
      }

      return false;
    }

    public function generateSql() : boolean{
        if($this->selectArguments > 0){
            $this->sql = "SELECT " . $this->selectArguments[0];

            if(count($this->selectArguments) > 1){
                foreach($this->selectArguments as $value){
                    $this->sql .= ", " . $this->selectArguments[1];
                }
            }

            $this->sql .= " FROM " . $this->entity->get_class();
            if(count($this->whereArguments) > 0){
                $this->sql .= " WHERE ";
                foreach($this->whereArguments as $value){
                    $this->sql .= $value[0] . ' ' . $value[1] . ' :' . $value[0];
                }
            }

            if(count($this->orderArguments) > 0){
                $this->sql .= " ORDER BY ";
                foreach($this->orderArguments as $value){
                    $this->sql .= $value[0] . ' ' . $value[1];
                }
            }
        } else {
            return false;
        }

        return true;
    }

    public function getComparisonOperator($operator) : String{
        if($operator == ALLOWED_COMPARISON['<']){
            return '<';
        }else if($operator == ALLOWED_COMPARISON['=']){
            return '=';
        }else if($operator == ALLOWED_COMPARISON['>']){
            return '>';
        }
        throw new InvalidRequestException();
    }

    public function getOrderOperator($operator) : String{
        if($operator == ALLOWED_COMPARISON['desc']){
            return 'desc';
        }else if($operator == ALLOWED_COMPARISON['asc']){
            return 'acc';
        }
        throw new InvalidRequestException();
    }

    public function getSql() : String{
        return $this->sql;
    }

    public function getEntity() : Model{
        return $this->entity;
    }
}
?>
