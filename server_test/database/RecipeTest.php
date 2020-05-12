<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\Recipe.php');
require_once(dirname(__FILE__, 3) . '\server\database\Recipe.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');




final class RecipeDatabaseTest extends TestCase
{

    protected $recipe;
    protected $mock;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Recipe"));
        $this->ingredient = new \database\Recipe($this->mock);
    }

    public function testInsert(): void{
        $this->assertEquals(
            '00000',
            $this->ingredient->insert(new \model\Recipe(1, 'nametest', 'descriptiontest', 0, 'countrycodetest', 'usernametest', 'mealtype_nametest', 0,  'time_of_day'))
        );
        $this->assertEquals(
            '23000',
            $this->ingredient->insert(new \model\Recipe(1, 'nametest', 'descriptiontest', 0, 'countrycodetest', 'usernametest', 'mealtype_nametest', 0,  'time_of_day'))
        );
        $this->assertEquals(
            '22001',
            $this->ingredient->insert(new \model\Recipe(112, 'nametest', 'descriptiontest', 0, 'countrycodetest', 'usernametest', 'mealtype_nametest', 0,  'time_of_day'))
        );
    }
}
