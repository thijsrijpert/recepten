<?php
declare(strict_types=1);

require_once(dirname(__FILE__, 3) . '\server\api\ingredient.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

use PHPUnit\Framework\TestCase;
use exception\NullPointerException;

final class ingredientApiTest extends TestCase
{

    protected $ingredient;

    public function setUp() : void{
        $this->ingredient = new api\Ingredient();
    }

    public function testErrorHandler(): void
    {
        $this->expectException(NullPointerException::class);
        $this->ingredient->error_handler(1, 'Undefined index: name', 1, 1);
    }
}
