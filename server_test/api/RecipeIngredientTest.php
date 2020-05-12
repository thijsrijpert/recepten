<?php
declare(strict_types=1);

require_once(dirname(__FILE__, 3) . '\server\api\RecipeIngredient.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

use PHPUnit\Framework\TestCase;
use exception\NullPointerException;

final class RecipeIngredientApiTest extends TestCase
{

    protected $recipeIngredient;

    public function setUp() : void{
        $this->recipeIngredient = new api\RecipeIngredient();
    }

    public function testErrorHandler(): void
    {
        $this->expectException(NullPointerException::class);
        $this->recipeIngredient->error_handler(1, 'Undefined index: recipe_id', 1, 1);
    }

}
