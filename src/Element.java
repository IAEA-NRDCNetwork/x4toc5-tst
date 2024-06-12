package zvv.x4;

/**
 * Element object
 *
 * @author          Viktor Zerkin, v.zerkin@iaea.org, v.zerkin@gmail.com
 * @version         2023-05-08
 * @since           2014-04-28
 *
 * Program:         Element.java
 * Property of:     International Atomic Energy Agency
 * Organization:    Nuclear Data Section
 *                  International Atomic Energy Agency (IAEA)
 *                  Wagramer Strasse 5, P.O.Box 100, A-1400
 *                  Vienna, Austria
 * Project:         Multi-Platform Nuclear Reaction Databases
 * Usage:           with proper acknolegement to the IAEA-NDS and author
 * Re-distribution: restricted while the project has not been finished
 * Modifications:   can be done with permission of IAEA-NDS and author
 * Note:            this is privately developed software and it comes with
 *                  NO WARRANTY
 */

class Element {
    int zz;
    String sym;
    String name;
    String mass;
    static Element[] ele = new Element[]{new Element(1, "H", "Hydrogen", "1.00794(7)"), new Element(2, "He", "Helium", "4.002602(2)"), new Element(3, "Li", "Lithium", "6.941(2)"), new Element(4, "Be", "Berylium", "9.012182(3)"), new Element(5, "B", "Boron", "10.811(7)"), new Element(6, "C", "Carbon", "12.0107(8)"), new Element(7, "N", "Nitrogen", "14.00674(7)"), new Element(8, "O", "Oxygen", "15.9994(3)"), new Element(9, "F", "Fluorine", "18.9984032(5)"), new Element(10, "Ne", "Neon", "20.1797(6)"), new Element(11, "Na", "Sodium", "22.989770(2)"), new Element(12, "Mg", "Magnesium", "24.3050(6)"), new Element(13, "Al", "Aluminium", "26.981538(2)"), new Element(14, "Si", "Silicon", "28.0855(3)"), new Element(15, "P", "Phosphorus", "30.973761(2)"), new Element(16, "S", "Sulphur", "32.066(6)"), new Element(17, "Cl", "Chlorine", "35.4527(9)"), new Element(18, "Ar", "Argon", "39.948(1)"), new Element(19, "K", "Potassium", "39.0983(1)"), new Element(20, "Ca", "Calcium", "40.078(4)"), new Element(21, "Sc", "Scandium", "44.955910(8)"), new Element(22, "Ti", "Titanium", "47.867(1)"), new Element(23, "V", "Vanadium", "50.9415(1)"), new Element(24, "Cr", "Chromium", "51.9961(6)"), new Element(25, "Mn", "Manganese", "54.938049(9)"), new Element(26, "Fe", "Iron", "55.845(2)"), new Element(27, "Co", "Cobalt", "58.933200(9)"), new Element(28, "Ni", "Nickel", "58.6934(2)"), new Element(29, "Cu", "Copper", "63.546(3)"), new Element(30, "Zn", "Zinc", "65.39(2)"), new Element(31, "Ga", "Gallium", "69.723(1)"), new Element(32, "Ge", "Germanium", "72.61(2)"), new Element(33, "As", "Arsenic", "74.92160(2)"), new Element(34, "Se", "Selenium", "78.96(3)"), new Element(35, "Br", "Bromine", "79.904(1)"), new Element(36, "Kr", "Krypton", "83.80(1)"), new Element(37, "Rb", "Rubidium", "85.4678(3)"), new Element(38, "Sr", "Strontium", "87.62(1)"), new Element(39, "Y", "Yttrium", "88.90585(2)"), new Element(40, "Zr", "Zirconium", "91.224(2)"), new Element(41, "Nb", "Niobium", "92.90638(2)"), new Element(42, "Mo", "Molybdenum", "95.94(1)"), new Element(43, "Tc", "Technetium", ""), new Element(44, "Ru", "Ruthenium", "101.07(2)"), new Element(45, "Rh", "Rhodium", "102.90550(2)"), new Element(46, "Pd", "Palladium", "106.42(1)"), new Element(47, "Ag", "Silver", "107.8682(2)"), new Element(48, "Cd", "Cadmium", "112.411(8)"), new Element(49, "In", "Indium", "114.818(3)"), new Element(50, "Sn", "Tin", "118.710(7)"), new Element(51, "Sb", "Antimony", "121.760(1)"), new Element(52, "Te", "Tellurium", "127.60(3)"), new Element(53, "I", "Iodine", "126.90447(3)"), new Element(54, "Xe", "Xenon", "131.29(2)"), new Element(55, "Cs", "Cesium", "132.90545(2)"), new Element(56, "Ba", "Barium", "137.327(7)"), new Element(57, "La", "Lanthanum", "138.9055(2)"), new Element(58, "Ce", "Cerium", "140.116(1)"), new Element(59, "Pr", "Praesodymium", "140.90765(2)"), new Element(60, "Nd", "Neodymium", "144.24(3)"), new Element(61, "Pm", "Promethium", ""), new Element(62, "Sm", "Samarium", "150.36(3)"), new Element(63, "Eu", "Europium", "151.964(1)"), new Element(64, "Gd", "Gadolinium", "157.25(3)"), new Element(65, "Tb", "Terbium", "158.92534(2)"), new Element(66, "Dy", "Dysprosium", "162.50(3)"), new Element(67, "Ho", "Holmium", "164.93032(2)"), new Element(68, "Er", "Erbium", "167.26(3)"), new Element(69, "Tm", "Thulium", "168.93421(2)"), new Element(70, "Yb", "Ytterbium", "173.04(3)"), new Element(71, "Lu", "Lutetium", "174.967(1)"), new Element(72, "Hf", "Hafnium", "178.49(2)"), new Element(73, "Ta", "Tantalum", "180.9479(1)"), new Element(74, "W", "Tungsten", "183.84(1)"), new Element(75, "Re", "Rhenium", "186.207(1)"), new Element(76, "Os", "Osmium", "190.23(3)"), new Element(77, "Ir", "Irirdium", "192.217(3)"), new Element(78, "Pt", "Platinum", "195.078(2)"), new Element(79, "Au", "Gold", "196.96655(2)"), new Element(80, "Hg", "Mercury", "200.59(2)"), new Element(81, "Tl", "Thallium", "204.3833(2)"), new Element(82, "Pb", "Lead", "207.2(1)"), new Element(83, "Bi", "Bismuth", "208.98038(2)"), new Element(84, "Po", "Polonium", ""), new Element(85, "At", "Astatine", ""), new Element(86, "Rn", "Radon", ""), new Element(87, "Fr", "Francium", ""), new Element(88, "Ra", "Radium", ""), new Element(89, "Ac", "Actinium", ""), new Element(90, "Th", "Thorium", "232.0381(1)"), new Element(91, "Pa", "Protactinium", "231.03588(2)"), new Element(92, "U", "Uranium", "238.0289(1)"), new Element(93, "Np", "Neptunium", ""), new Element(94, "Pu", "Plutonium", ""), new Element(95, "Am", "Americium", ""), new Element(96, "Cm", "Curium", ""), new Element(97, "Bk", "Berkelium", ""), new Element(98, "Cf", "Californium", ""), new Element(99, "Es", "Einsteinium", ""), new Element(100, "Fm", "Fermium", ""), new Element(101, "Md", "Mendelevium", ""), new Element(102, "No", "Nobelium", ""), new Element(103, "Lr", "Lawrencium", ""), new Element(104, "Rf", "Rutherfordium", ""), new Element(105, "Db", "Dubnium", ""), new Element(106, "Sg", "Seaborgium", ""), new Element(107, "Bh", "Bohrium", ""), new Element(108, "Hs", "Hassium", ""), new Element(109, "Mt", "Meitnerium", ""), new Element(110, "Ds", "Darmstadtium", ""), new Element(111, "Rg", "Roentgenium", ""), new Element(112, "Cn", "Copernicium", ""), new Element(113, "Nh", "Nihonium", ""), new Element(114, "Fl", "Flerovium", ""), new Element(115, "Mc", "Moscovium", ""), new Element(116, "Lv", "Livermorium", ""), new Element(117, "Ts", "Tennessine", ""), new Element(118, "Og", "Oganesson", "")};

    Element(int n, String string, String string2, String string3) {
        this.zz = n;
        this.sym = string;
        this.name = string2;
        this.mass = string3;
    }

    public String toString() {
        String string = this.zz + "-" + this.sym;
        return string;
    }

    public static Element getEle(int n) {
        String string = "*";
        if (n > 0 && n < ele.length) {
            return ele[n - 1];
        }
        return null;
    }

    public static Element getElement(int n) {
        if (n - 1 < 0) {
            return null;
        }
        if (n - 1 >= ele.length) {
            return null;
        }
        return ele[n - 1];
    }
}
