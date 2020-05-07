<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\Ingredient.php');
require_once(dirname(__FILE__, 3) . '\server\database\Ingredient.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');




final class IngredientDatabaseTest extends TestCase
{

    protected $religion;
    protected $mock;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Ingredient"));
        $this->ingredient = new database\Ingredient($this->mock);
    }

    public function testInsert(): void{
        $this->assertEquals(
            '00000',
            $this->ingredient->insert(new \model\Ingredient('ingredienttest','descriptiontest',0,'usernametest'))
        );
        $this->assertEquals(
            '23000',
            $this->ingredient->insert(new \model\Ingredient('ingredienttest','descriptiontest',0,'usernametest'))
        );
        $this->assertEquals(
            '22001',
            $this->ingredient->insert(new \model\Ingredient('kaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaas','descriptiontest',0, 'usernametest'))
        );
    }
}
