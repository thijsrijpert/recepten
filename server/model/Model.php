<?php
namespace model;
abstract class Model implements \JsonSerializable {
    abstract function getVariables();
}
?>
