<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\RecipeIngredient.php');
require_once(dirname(__FILE__, 3) . '\server\database\RecipeIngredient.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');




final class RecipeIngredientDatabaseTest extends TestCase
{

    protected $recipeIngredient;
    protected $mock;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Recipe_Ingredient WHERE  recipe_id = :recipe_id"));
        $this->recipeIngredient = new \database\RecipeIngredient($this->mock);
    }

    public function testInsert(): void{
        $this->assertEquals(
            '00000',
            $this->recipeIngredient->insert(new \model\Recipe_Ingredient(999, 'ingredienttest'))
        );
        $this->assertEquals(
            '23000',
            $this->recipeIngredient->insert(new \model\Recipe_Ingredient(999, 'ingredienttest'))
        );
        $this->assertEquals(
            '22001',
            $this->recipeIngredient->insert(new \model\Recipe_Ingredient(9999, 'ingredienttest'))
        );
    }
}
