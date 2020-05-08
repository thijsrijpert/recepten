<?php
namespace model;
abstract class Model implements \JsonSerializable {
    abstract function getVariables();

    function setUpJson(array $object_vars){
        $json = "[";
        foreach($vars as $key => $value){
            if($key != null){
                $json .= "'$key' => $value,";
            }
        }
        $json .= "]";
        return $json;
    }
}
?>
