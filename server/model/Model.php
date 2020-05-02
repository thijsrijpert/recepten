<?php
namespace model;
abstract class Model{
    public function getVariables(){
        return get_object_vars($this);
    }
}
?>
