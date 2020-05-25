<?php
namespace model;
abstract class Model implements \JsonSerializable {
    abstract function getVariables();

    function setUpJson(array $vars){
       $json = '{';
       $first = true;
        foreach($vars as $key => $value){
            if($key != null && $value != null){
                if(!$first){
                    $json .= ',"' . $key . '" : "' . $value . '"';
                }else{
                  $json .= '"' . $key . '" : " ' . $value . '"';
                  $first = false;
                }
            }
        }
        $json .= '}';
        return \json_decode($json);
    }
}
?>
