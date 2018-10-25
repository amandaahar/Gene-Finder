
/**
 * Write a description of Genes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.File;
public class Genes 
{
    
    public int findStopCodon(String dnaStr,
                             int startIndex,
                             String stopCodon)
    {
        int currIndex = dnaStr.indexOf(stopCodon,startIndex+3);
        while (currIndex != -1)
        {
            int diff = currIndex - startIndex;
            if (diff % 3 == 0)
            {
                return currIndex;
            }
            else
            {
                currIndex = dnaStr.indexOf(stopCodon, currIndex + 1);
            }
            
        }
        return -1;
    }
    
    public String findGene(String dna, int where)
    {
        int startIndex = dna.indexOf("atg", where);
        if (startIndex == -1)
        {
            return "";
        }
        int taaIndex = findStopCodon(dna,startIndex,"taa");
        int tagIndex = findStopCodon(dna,startIndex,"tag");
        int tgaIndex = findStopCodon(dna,startIndex,"tga");
        //int temp = Math.min(taaIndex,tagIndex);
        //int minIndex = Math.min(temp, tgaIndex);
        //newstuff
        int minIndex = 0;
        if (taaIndex == -1 || (tgaIndex != -1 && tgaIndex < taaIndex))
        {
            minIndex = tgaIndex;
        }
        else
        {
            minIndex = taaIndex;
        }
        
        if (minIndex == -1 || (tagIndex != -1 && tagIndex < minIndex))
        {
            minIndex = tagIndex;
        }
        
        if (minIndex == -1)
        {
            return "";
        }
     
        return dna.substring(startIndex,minIndex + 3);
    }
    
    /*
    public void printAllGenes(String dna)
    {
        int startIndex = 0;
        
        while (true)
        {
            String currentGene = findGene(dna, startIndex);
            
            if (currentGene.isEmpty())
            {
                break;
            }
            
            System.out.println(currentGene);
            startIndex = dna.indexOf(currentGene, startIndex) + 
                         currentGene.length();
        }
        
    }
    */
    public StorageResource getAllGenes(String dna)
    {
        StorageResource geneList = new StorageResource();
        
        int startIndex = 0;
        
        while (true)
        {
            String currentGene = findGene(dna, startIndex);
            
            if (currentGene.isEmpty())
            {
                break;
            }
            
            geneList.add(currentGene);
            startIndex = dna.indexOf(currentGene, startIndex) + 
                         currentGene.length();
        }
        return geneList;
    }
    
    public void processGenes(StorageResource sr)
    {
        int numGenes = 0;
        int numLongerSixty = 0;
        int numCGRatio = 0;
        int currentGeneLength = 0;
        
        for(String gene : sr.data())
        {
            numGenes += 1;
        }
        
        System.out.println("Number of genes = " + numGenes);
        for(String gene : sr.data())
        {
            if(gene.length() > 60)
            {
                System.out.println(gene);
                numLongerSixty += 1;
            }
        }
        System.out.println("Number of genes longer than 60 is " + numLongerSixty);
        
        for(String gene : sr.data())
        {
            if(cgRatio(gene) > .35)
            {
                System.out.println(gene);
                numCGRatio += 1;
            }
        }
        System.out.println("Number of genes with cg ratio > .35 is " + numCGRatio);
        
        for(String gene : sr.data())
        {
            if( gene.length() > currentGeneLength)
            {
                currentGeneLength = gene.length();
            }
            
        }
        System.out.println("The longest gene is " + currentGeneLength + " letters long");
        
        
        
    }
    
   
    
    public int countGenes(String dna)
    {
        int startIndex = 0;
        int count = 0;
        while (true)
        {
            String currentGene = findGene(dna, startIndex);
            
            if (currentGene.isEmpty())
            {
                //return 0;
                break;
            }
            
            count += 1;
            startIndex = dna.indexOf(currentGene, startIndex) + 
                         currentGene.length();
        }
        return count;
    }
    
    public double cgRatio(String dna)
    {
        int count = 0;
        int start = 0;
        
        while (start < dna.length())
        {
            if (dna.substring(start,start + 1).equals("c")|| 
                dna.substring(start, start + 1).equals("g"))
            {
                count += 1;
            }
            start += 1;
        }
        
        //System.out.println("Count is now " + count);
        //double ratio = (float)count/dna.length();
        return ((double) count) /dna.length(); 
    }
    
    public int countCTG(String dna)
    {
        int start = dna.indexOf("ctg");
        int count = 0;
        
        if (start == -1)
        {
            return 0;
        }
        
        while (start <= dna.length()-3 && start != -1)
        {
            count += 1;
            start = dna.indexOf("ctg", start + 3);
        }
        return count;
    }
    
    public int howMany(String a, String b)
    {
        int start = b.indexOf(a);
        int count = 0;
        if (start == -1)
        {
            return 0;
        }
        
        while (start <= b.length()-1 && start != -1)
        {
            count += 1;
            start = b.indexOf(a, start + a.length());
        }
        return count; 
    }
    
    public void testFindStop()
    {
        
        // 012345678901234567890
        
        String dna = "xxxysssssTAAxxxyyxxx";
        int dex = findStopCodon(dna,0,"TAA");
        if(dex != 9)System.out.println("error");
        System.out.println(dex);
        int dex2 = findStopCodon(dna,9,"TAA");
        System.out.println(dex2);
        
        
       
    }
    
    public void testFindGene()
    {
        //            012345678901234567890
        String dna = "xxxatgctgtaaxxxx";
        String gene = findGene(dna, 1);
        System.out.println(gene);
        String dna2 = "xxxatgctgtgaxxxx";
        String gene2 = findGene(dna2,1);
        System.out.println(gene2);
        String dna3 = "xxxatgctgtagxxxx";
        String gene3 = findGene(dna3,1);
        System.out.println(gene3);
    }
    
    public void testProcessGenes(String dna)
    {
        System.out.println("Testing on " + dna);
        StorageResource genes = getAllGenes(dna);
        for (String g : genes.data())
        {
            System.out.println(g);
        }
        
        processGenes(genes);
    }
    
    public void testProcessGenes2()
    {
        // testing longer than 9 genes
        testProcessGenes("atggattagtagatgcgttaaatgtga");
        testProcessGenes("atgctgctgtagatcatgcagctgtaa");
        
        //testing cg ratio greater than .35
        testProcessGenes("atgcgcgcgcgctagatggaaaaataaatg");
    }
    
    public void testRealDNA()
    {
        //FileResource fr = new FileResource("brca1line.txt");
        FileResource fr = new FileResource("StringsQ3.txt");
        String dna = fr.asString().toLowerCase();
        
        StorageResource genes = getAllGenes(dna);
        
        processGenes(genes);
        
        int ctg = countCTG(dna);
        System.out.println("ctg count = " + ctg);
    }
    
    
    public void testStorage(String dna)
    {
        System.out.println("Testing on " + dna);
        StorageResource genes = getAllGenes(dna);
        for (String g : genes.data())
        {
            System.out.println(g);
        }
        
        //processGenes(genes);
        
    }
    
    public void testPrintGene(String dna)
    {
        System.out.println("Testing on " + dna);
        //printAllGenes(dna);
    }
    public void testPrintAndStorage()
    {
        //
        //testPrintGene("atggattagtagatgcgttaaatgtga");
        //testPrintGene("");
        
        // testing longer than 9 genes
        testStorage("atggattagtagatgcgttaaatgtga");
        testStorage("atgctgctgtagatcatgcagctgtaa");
        
    }
    public void testHowMany()
    {
        String a = "atg";
        String b = "atgatg";
        int atgCount = howMany(a,b);
        System.out.println(atgCount);
        String a2 = "GAA";
        String b2 = "ATGAACGAATTGAATC";
        int gaaCount = howMany(a2,b2);
        System.out.println(gaaCount);
        
        String a3 = "atg";
        String b3 = "bacgbhbh";
        int atgCount2 = howMany(a3,b3);
        System.out.println(atgCount2);
        
    }
    public void testCountGenes()
    {
        String dna = "atgcattaagacgacatgcatgacgactgatgaatgbactag";
        int genes = countGenes(dna);
        System.out.println(genes);
        System.out.println("answer should be 3");
        
        String dna2 ="ATGTAAGATGCCCTAGT";
        String dna3 = dna2.toLowerCase();
        int genes2 = countGenes(dna3);
        System.out.println(genes2);
        System.out.println("answer should be 2");
        
    }
    public void testCGRatio()
    {
        String dna = "gtgctgtac";
        double cgRatio = cgRatio(dna);
        System.out.println(cgRatio);
        System.out.println("Answer should be " + (5.0/9));
        
        String dna2 = "atgctgtaaa";
        double cgRatio2 = cgRatio(dna2);
        System.out.println(cgRatio2);
        System.out.println("Answer should be " + (3.0/10));
    }
    
    public void testCTGCount()
    {
        String dna = "ctgctgtagatctg";
        int ctgCount = countCTG(dna);
        System.out.println(ctgCount);
        System.out.println("Answer should be 3");
        
    }

}
