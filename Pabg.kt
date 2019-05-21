package fr.pixelsparkle.pabg

import java.util.*

fun main(args: Array<String>){

    val gameVersion = "1.1.2"

    //TODO: NAMES
    //Mettez les pseudos du héro sur les deux guillemets juste en dessous.

    val boss: Boss = Boss("Boss", 100, 20)
    val hero: Hero = Hero("Moi", 100, 5, 25)

    var running = false
    var starting = true

    while(starting){
        println("/************** PixelSparkle Advanced Game Battle **************\\\n" +
                "Version actuelle : "+gameVersion+"\n" +
                "Développé par PixelSparkle. Fait avec amour, pour l'amour des jeux.\n" +
                "Dès que vous ètes prets a jouer, marquez (y), sinon (n).\n" +
                "\\***************************************************************/")

        val entrykey = readLine()

        if(entrykey.equals("y", true)){
            starting = false
            running = true

            print("Quel est votre pseudo ? :")
            val pseudokey = readLine()
            hero.setName(pseudokey.toString())
            if(hero.getName() == "") {hero.setName("Moi")}
        }
        else if(entrykey.equals("n", true)){
            starting = false
            println("A bientôt !")
            System.exit(0)
        }
    }
    while(running){
        var bossAlive = true
        var skip = false

        var randomHeroDamage = Random().nextInt(hero.getDmg())
        var randomBossDamage = Random().nextInt(boss.getDmg())
        var randomBossCritDamage = Random().nextInt(10)
        var randomBossRegen = Random().nextInt(10)

        println("/************** Menu de combat **************\\\n")
        if(hero.getDmg() < 100){
            println(hero.getName() + " >> Vie: " + hero.getLife() + " HP / Régénérations: "+hero.getHeal()+" / Dégats: " + hero.getDmg())
        }else{
            println(hero.getName() + " >> Vie: " + hero.getLife() + " HP / Régénérations: "+hero.getHeal()+" / Dégats: [MAX]")
        }
        if(boss.getDmg() < 100){
            println(boss.getName() + " >> Vie: " + boss.getLife() + " HP / Dégats: " + boss.getDmg())
        }else{
            println(boss.getName() + " >> Vie: " + boss.getLife() + " HP / Dégats: [MAX]")
        }
        println("")
        println("[X] Quitter \t [H] Hit \t [R] Regen")
        println("\\**********************************************/")

        var actionkey = readLine()

        if (actionkey == "X") {
            running = false
            println("A bientôt !")
            System.exit(0)
        }

        else if (actionkey == "O") {
            println("/********** ONE SHOT ! **********\\\n" +
                    "Vous avez one shot le boss\n" +
                    "Hit = boss.setLife(0) [-"+boss.getLife()+"]\n" +
                    "\\*******************************/")
            boss.setLife(0)
        }

        else if (actionkey == "S") {
            println("/************ SUICIDE ***********\\\n" +
                    "Vous vous êtes suicidés !\n" +
                    "Dégats reçus = -"+hero.getLife()+" HP\n"+
                    "\\*******************************/")
            hero.setLife(0)
        }
        else if(actionkey == "H"){
            boss.setLife(boss.getLife() - randomHeroDamage)
            println("/************ HIT ***********\\\n" +
                    "Vous attaquez le boss ! \n" +
                    "Hit = "+randomHeroDamage+" HP\n" +
                    "\\****************************/")
        }
        else if(actionkey == "R"){
            if(hero.getHeal() >0){
                hero.setHeal(hero.getHeal() - 1)
                hero.setLife(hero.getDefLife())
                println("/************* REGEN ************\\\n" +
                        "Vous vous êtes régénérés ! !\n" +
                        "Heals restants : "+hero.getHeal()+"\n"+
                        "\\*******************************/")
            }else{
                println("/************* REGEN ************\\\n" +
                        "Vous n'avez plus de heal\n" +
                        "\\*******************************/")
                skip = true
            }
        }

        if(boss.getLife() <= 0){
            println("/************** WIN *************\\\n" +
                    "FIN DU MATCH, VOUS AVEZ GANGÉ !!\n" +
                    "Vie restante :"+hero.getLife()+" HP\n" +
                    "\\*******************************/")
            running = false
            bossAlive = false
        }

        if(bossAlive){
            if(!skip){
                if(randomBossDamage == 0){
                    println("/************ ESQUIVE ***********\\\n" +
                            "Vous avez esquivé ! :D !\n" +
                            "\\*******************************/")
                }
                else{
                    if(randomBossCritDamage >=9){
                        hero.setLife(hero.getLife() - randomBossDamage*3)
                        println("/*********** BOSS HIT***********\\\n" +
                                "COUP CRITIQUE du boss !\n" +
                                "Dégats reçus : -"+randomBossDamage*3+" HP !\n" +
                                "\\*******************************/")
                    } else {
                        hero.setLife(hero.getLife() - randomBossDamage)
                        println("/*********** BOSS HIT***********\\\n" +
                                "Le boss vous attaque !\n" +
                                "Dégats reçus : -"+randomBossDamage+" HP !\n" +
                                "\\*******************************/")
                    }
                }
            }
            if(boss.getLife() <= 35){
                if(randomBossRegen >=7){
                    boss.setLife(boss.getDefLife())
                    println("/*********** BOSS REGEN **********\\\n" +
                            "Le boss s'est régénéré !\n" +
                            "\\*******************************/")
                }
            }
        }
        //TODO: ENDGAME
        if(hero.getLife() <= 0){
            println("/************* LOOSE *************\\\n" +
                    "FIN DU MATCH, VOUS AVEZ PERDU ...\n" +
                    "\\*******************************/")
            running = false
        }
    }
}

class Hero(name: String, life: Int, heal: Int, dmg: Int){
    private var hName = name
    private var hLife = life
    private var hDefLife = life
    private var hDmg = dmg
    private var hHeal= heal

    fun setDmg(value: Int) {hDmg = value}
    fun setLife(value: Int) {hLife = value}
    fun setHeal(value: Int) {hHeal = value}
    fun setName(value: String) {hName = value}

    fun damage(value: Int) {hLife - value}

    fun getName() = hName
    fun getLife() = hLife
    fun getDefLife() = hDefLife
    fun getHeal() = hHeal
    fun getDmg() = hDmg
}
class Boss(name: String, life: Int, dmg: Int){
    private var bName = name
    private var bLife = life
    private var hDefLife = life
    private var bDmg = dmg
    fun restore(value: Int) = bLife + value

    fun setDmg(value: Int) {bDmg = value}
    fun setLife(value: Int) {bLife = value}
    fun setName(value: String) {bName = value}

    fun getName() = bName
    fun getLife() = bLife
    fun getDefLife() = hDefLife
    fun getDmg() = bDmg
}
