/*
 * Copyright (c) 2015, IETR/INSA of Rennes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

#include "stdio.h"
#include "trackimg.h"

#include "opencv2/core/core.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"
using namespace std;
using namespace cv;

struct Tar_properties
{
    Mat fea;
    Mat pos;
    Mat siz;
    Mat posres;
    Mat pnew;
    Mat feaN;
    int flag;
} ;

struct parameter_OMP
{
    double err;
    double nu;
};

struct step_windows
{
    int d;
    int n;
};

Mat p2(2, 2, CV_64F); //coordinate of selected object
Mat p(2, 1, CV_64F); //coordinate of selected object - top-left point
Mat sz(2, 1, CV_64F); //size of selected object
Mat p_reg;			//for return of function Region_seg 
int nf=200;	//size of Tar
int nff=100;

//im_seg_resize
Mat im_seg_resize(Mat A,double h, double w, int wbh, int wbw, Mat sz )
{
    Mat a;
    A.copyTo(a);
    int m=a.rows;
    int n=a.cols;
    int z=3; //for color image
    //end coordinate of sliding windows
    double wn;
    wn=n-w+1;
    double wm;
    wm=m-h+1;
    //number of times to do sliding windows
    int x=0,y=0;
    y=cvFloor(wm/wbh)+1;
    x=cvFloor(wn/wbw)+1;

    int jj=0,j=0;
    int ii=0;int i=0;
    Mat subim(sz.at<double>(0,0)*sz.at<double>(1,0)*3+2, 1, CV_64F, Scalar::all(0));

    for (i=0; i<wm; i+=wbh)	//vertical
    {
        for (j=0; j<wn; j+=wbw)	//horizon
        {

            Mat ROI_sb = a(Rect(j, i, w, h));
            Mat sb_1;
            ROI_sb.copyTo(sb_1);
            Mat sb = sb_1.reshape(1, sz.at<double>(1,0)*sz.at<double>(0,0)*z); //reshape(int cn, int rows)
            sb.resize(sb.rows+2, 0);
            sb.at<double>(sb.rows-2, 0) = ii;
            sb.at<double>(sb.rows-1, 0) = jj;

            transpose(subim, subim);
            subim.resize(subim.rows+1, 0);
            transpose(subim, subim);
            sb.col(0).copyTo(subim.col(subim.cols - 1));

            jj++;
        }
        jj=0;
        ii++;
    }
    Mat subim_sup=subim(Rect(1,0, subim.cols-1,subim.rows));
    Mat subim1;
    subim_sup.copyTo(subim1);

    return subim1;
}

//Region_seg
Mat Region_seg(Mat A, Mat pt, Mat st, Mat sc)	//return new area from image A
{
    int m=A.rows;
    int n=A.cols;
    int z=3;
    Mat pc=pt+0.5*st;			//central of selected object
    pc.at<double>(0,0)=cvCeil(pc.at<double>(0,0));	//round to nearest integer
    pc.at<double>(1,0)=cvCeil(pc.at<double>(1,0));
    pc.convertTo(pc,CV_64F);
    Mat s=st.mul(sc);			//selected object*scale
    s.at<double>(0,0)=cvCeil(s.at<double>(0,0));	//round to nearest integer
    s.at<double>(1,0)=cvCeil(s.at<double>(1,0));
    s.convertTo(s,CV_64F);

    //calculate top-left position and bottom-right position of new area
    Mat pl=(pc-0.5*s);
    pl.at<double>(0,0)=cvCeil(pl.at<double>(0,0));
    pl.at<double>(1,0)=cvCeil(pl.at<double>(1,0));
    Mat M1(2,1,CV_64F);
    M1.at<double>(0,0)=1;
    M1.at<double>(1,0)=1;
    pl=pl+M1;
    Mat pr=(pc+0.5*s);
    pr.at<double>(0,0)=cvFloor(pr.at<double>(0,0));
    pr.at<double>(1,0)=cvFloor(pr.at<double>(1,0));
    pr=pr-M1;
    //modify pl and pr to openCV coordinate (-1 for each coordinate)
    pl=pl-M1;
    pr=pr-M1;
    if(pl.at<double>(0,0) <= 0 )
    {pl.at<double>(0,0) = 0;}
    if(pl.at<double>(1,0) <= 0 )
    {pl.at<double>(1,0) = 0;}

    //set limit if result calculate is bigger than image
    if (pr.at<double>(0,0) > n-1)
    {pr.at<double>(0,0)=n-1;}
    if (pr.at<double>(1,0) > m-1)
    {pr.at<double>(1,0)=m-1;}

    //update new top left position of region
    pl.copyTo(p_reg);
    //Copy new region from A to R
    Mat R((pr.at<double>(1,0) - pl.at<double>(1,0) +1), (pr.at<double>(0,0)-pl.at<double>(0,0) +1), CV_64FC3);
    Mat tmp = A(Rect(pl.at<double>(0,0) ,pl.at<double>(1,0) ,pr.at<double>(0,0)-pl.at<double>(0,0) +1 ,pr.at<double>(1,0)-pl.at<double>(1,0)+1));
    tmp.copyTo(R);
    return R;	//return new region
}

//Region_Negative
Mat Region_Negative(Mat A, int wbh, int wbw, Mat Tar_pos,Mat Tar_siz, Mat sr, int nff)
{
    Mat A_a; A.copyTo(A_a);
    int m=A_a.rows;
    int n=A_a.cols;
    int z=3;
    Tar_pos.col(nff-1).copyTo(p.col(0));
    Tar_siz.col(nff-1).copyTo(sz.col(0));

    Mat sub = A_a(Rect(p.at<double>(0,0) ,p.at<double>(1,0) ,sz.at<double>(0,0)-1 ,sz.at<double>(1,0)-1));
    randn(sub,0,122);
    sub.copyTo(A_a(Rect(p.at<double>(0,0) ,p.at<double>(1,0) ,sz.at<double>(0,0)-1 ,sz.at<double>(1,0)-1)));
    //calculate new ROI, that possibility to contain object:

    Mat Reg=Region_seg(A_a,p,sz,sr);

    Mat subim=im_seg_resize(Reg, sz.at<double>(1,0), sz.at<double>(0,0), wbh, wbw, Tar_siz.col(0));
    Mat FeaN(subim.rows-2, subim.cols, CV_64F);
    Mat ROI_subim = subim(Rect(0, 0, subim.cols, subim.rows-2));
    ROI_subim.copyTo(FeaN);

    return FeaN;
}


static Tar_properties Tar;
static parameter_OMP param;
static step_windows wbw;
static step_windows wbh;
static Mat Sca_R (2,1,CV_64F); //scales of region for retrieval
static Mat Sca_T (2,1,CV_64F);
static Mat Sca_R_N (2,1,CV_64F);
static Mat Sca_R_O (2,1,CV_64F);//scales of region for retrieval when occlusion is detected
static Mat sf (1,22,CV_64F);
static int wbw_d=4; //for object segment in ROI (for animal: wbw_d=4 ; wbw_n=10)
static int wbh_d=4;
static int wbw_n=10; //for background sample
static int wbh_n=10;
static int vg=1;   //scale Gaussian noise for initial samples
static double cr=30; //Gaussian compression rate in lasso recognition (for animal: 30 - 3/4)
static double itr=3;  //iterative times for random compression in lasso recognition

//circular shift one row from up to down
void shiftRows(Mat& mat) 
{

    Mat temp;
    Mat m;
    int k = (mat.rows-1);
    mat.row(k).copyTo(temp);
    for(; k > 0 ; k-- ) {
        m = mat.row(k);
        mat.row(k-1).copyTo(m);
    }
    m = mat.row(0);
    temp.copyTo(m);

}

//circular shift n rows from up to down if n > 0, -n rows from down to up if n < 0
void shiftNRows(Mat& mat,int n) 
{

    if( n < 0 )
    {

        n = -n;
        flip(mat,mat,0);
        for(int k=0; k < n;k++) {
            shiftRows(mat);
        }
        flip(mat,mat,0);

    }
    else
    {

        for(int k=0; k < n;k++)
        {
            shiftRows(mat);
        }
    }

}

//circular shift n columns from left to right if n > 0, -n columns from right to left if n < 0
void shiftCols(Mat& mat, int n) 
{
    if(n < 0)
    {
        n = -n;
        flip(mat,mat,1);
        transpose(mat,mat);
        shiftNRows(mat,n);
        transpose(mat,mat);
        flip(mat,mat,1);
    }
    else
    {
        transpose(mat,mat);
        shiftNRows(mat,n);
        transpose(mat,mat);
    }
}

Mat hist(Mat data, Mat nbins)
{
    //frequency of each element of nbins in data
    //input data and nbins is rows vetor
    Mat result(1, nbins.cols, CV_64F);
    int freq=0;
    for (int jc=0; jc<nbins.cols; jc++)	//each element of nbins
    {
        for (int ic=0; ic<data.cols; ic++)	//each element of data
        {
            if(nbins.at<double>(0,jc) == data.at<double>(0,ic))
            {
                freq++;
            }
        }
        result.at<double>(0,jc) = freq;
        freq = 0;
    }
    return result;
}	

Mat find_element_equal_less_zero(Mat scr)
{
    vector<double> sign;
    int ind=0;
    for(int ic=0; ic<scr.cols; ic++)
    {
        for (int jh=0; jh<scr.rows; jh++)
        {
            if (scr.at<double>(jh,ic) <= 0)
            {sign.push_back(ind);}
            ind++;
        }
    }
    Mat sign_return(1, sign.size(), CV_64F);
    for (int f=0;f<sign.size();f++)
    {
        sign_return.at<double>(0,f) = sign[f];
    }
    transpose(sign_return, sign_return);
    return sign_return;
}

Mat setdiff(Mat scr1, Mat scr2)
{
    //find an element in scr1 that different with element in scr 2 (scr 2 is an Scalar)
    for(int ic=0; ic<scr2.cols; ic++)
    {
        for (int jc=0; jc<scr1.cols; jc++)
        {
            if(scr2.at<double>(0,ic) == scr1.at<double>(0,jc))
            {
                scr1.at<double>(0,jc) = -1;
            }
        }
    }
    vector<double> result;
    for (int ic=0; ic<scr1.cols; ic++)
    {
        if(scr1.at<double>(0,ic) != -1)
        {
            result.push_back(scr1.at<double>(0,ic));
        }
    }
    Mat result1(1, result.size(), CV_64F);
    for(int ic=0; ic<result.size(); ic++)
    {
        result1.at<double>(0,ic) = result[ic];
    }
    return result1;
}

Mat sign_element_matrix(Mat source, Mat position)
{
    //input is a column matrix for source and line matrix for position - return a colum matrix
    double temp;
    vector<double> sign;
    for (int ic=0; ic<position.cols; ic++)
    {
        temp = source.at<double>(position.at<double>(0,ic), 0);
        if (temp > 0)
        {sign.push_back(1);}
        else if (temp == 0)
        {sign.push_back(0);}
        else
        {sign.push_back(-1);}
    }

    Mat sign_return1(sign.size(), 1, CV_64F);
    for (int jh=0; jh<sign_return1.rows; jh++)
    {
        sign_return1.at<double>(jh,0) = sign[jh];
    }

    return sign_return1;
}

Mat lars_lu(Mat y, Mat X, double err, double nu)
{
    //Dicitionary X, vector y,  nu is sparsity. Return vector coefficient
    /*================================================ LARS ALGORITHMS ==========================================*/
    int m=X.rows;
    int n=X.cols;
    Mat yr;
    //initialization for residual
    y.copyTo(yr);
    //S contains index of atoms in dictionary X
    Mat S(1, n, CV_64F);
    for (int h=0; h<n; h++)
    {
        S.at<double>(0,h)=h;
    }
    //initialize coefficient beta = 0
    Mat beta(n, 1, CV_64F, Scalar(0));
    //project yr to dictionary X
    int i=0;
    Mat X_transpose;
    transpose(X,X_transpose);
    Mat c=X_transpose*yr;
    Mat c_a = abs(c);
    //find max coefficient
    double minVal;
    double c_m;
    Point minLoc;
    Point maxLoc;
    minMaxLoc(c_a, &minVal, &c_m, &minLoc, &maxLoc);

    //find location of max coefficients (index of atom that have max projection)
    int ind=0;
    vector<double> Sa_array;
    for (int jh=0; jh<c_a.rows; jh++)
    {
        if(c_a.at<double>(jh,0)==c_m)
        {
            Sa_array.push_back(ind);
        }
        ind++;
    }

    Mat Sa (1, Sa_array.size(), CV_64F);
    for (int h=0;h<Sa_array.size();h++)
    {
        Sa.at<double>(0,h)=Sa_array[h];
    }
    /*================================ WHILE LOOP ============================ */
    while(i<=nu && norm(yr)>err)
    {
        i++;
        Mat sign_c_Sa = sign_element_matrix(c, Sa); //sign(c(Sa))
        transpose(sign_c_Sa, sign_c_Sa);	//sign(c(Sa))'
        repeat(sign_c_Sa, m,1, sign_c_Sa);	//repmat(sign(c(Sa))',m,1)
        Mat X_temp(X.rows, Sa.cols, CV_64F);
        for(int h=0; h < Sa.cols; h++) {
        	X.col(Sa.at<double>(0,h)).copyTo(X_temp.col(h));
        }		//X(:,Sa)
        Mat Xa = sign_c_Sa.mul(X_temp);		//repmat(sign(c(Sa))',m,1).*X(:,Sa)
        Mat Xa_tranpose;
        transpose(Xa, Xa_tranpose);
        Mat C_1= Xa_tranpose*yr;
        double C = C_1.at<double>(0,0);

        Mat Ga_temp=Mat::eye(Xa.cols, Xa.cols, CV_64F);
        Mat Ga = Xa_tranpose*Xa + 0.00000001*Ga_temp;
        Mat one_1 = Mat::ones(Sa.cols , 1, CV_64F);
        Mat one_1_transpose;
        transpose(one_1, one_1_transpose);
        Mat Ga_inverse1; Mat Ga_inverse;
        Ga.convertTo(Ga_inverse1, CV_64F); //convert float to double
        Ga_inverse=Ga_inverse1.inv();
        Ga_inverse.convertTo(Ga_inverse, CV_64F);
        Mat Aa = (one_1_transpose * Ga_inverse * one_1);
        pow(Aa, -0.5, Aa);		//we can use in this case bcs Aa is sure a Scalar -- what is that mean ^(-1/2) in matrix calculation?
        double Aa_scalar = Aa.at<double>(0,0);
        Mat Wa=Aa_scalar * Ga_inverse * one_1;
        Mat Ua = Xa * Wa;

        /*============ LOOP EXIT WHEN SEARCHING REACH TO THE END ELEMENT OF DICTIONARY =============*/
        if (i==n)
        {
            Mat yr_transpose;
            transpose(yr, yr_transpose);
            Mat r_h;
            r_h=yr_transpose*Ua;
            double r_h_Scalar = r_h.at<double>(0,0);	//to avoid error at line .mul after
            sign_c_Sa = sign_element_matrix(c, Sa);

            Mat beta_increament = (r_h_Scalar*sign_c_Sa).mul(Wa);
            vector<double> beta_increment_vector;
            for(int ic=0; ic<beta_increament.rows; ic++)
            {beta_increment_vector.push_back(beta_increament.at<double>(ic,0));}

            for (int h=0; h<Sa.cols; h++)
            {
                beta.at<double>(Sa.at<double>(0,h) ,0) = beta.at<double>(Sa.at<double>(0,h) ,0) + beta_increment_vector[h];
            }
            yr = y-X*beta;
            return beta;
        }
        /*===========================================================================================*/

        Mat a;
        a = X_transpose*Ua;
        Mat Sc = setdiff(S, Sa);	//Sc is unactive set

        /*========================= NOT REACH THE END ELEMENT OF DICTIONARY YET, SO, FIND AMOUNT TO UPDATE BETA (AMOUNT IS u(gamma) AND UPDATE BETA ============================*/
        /*=========================== we do and update coefficient beta with the formular: u(gamma) = uA + AMOUNT*Ua = uA + AMOUNT*(Xa*Wa) ==================================== */

        // ============ calculate set of gamma ======================= //
        vector<double> Sr_vector;
        for (int j=0; j<Sc.cols; j++)
        {
            double v1 = (C - c.at<double>(Sc.at<double>(0,j), 0)) / (Aa_scalar - a.at<double>(Sc.at<double>(0,j), 0));
            double v2 = (C + c.at<double>(Sc.at<double>(0,j), 0)) / (Aa_scalar + a.at<double>(Sc.at<double>(0,j), 0));
            Sr_vector.push_back(v1);
            Sr_vector.push_back(v2);
        }
	    Mat Sr(1, Sr_vector.size(), CV_64F);	//tranfer set of gamma to matrix type
        for (int h=0;h<Sr_vector.size();h++)
        {Sr.at<double>(0,h)=Sr_vector[h];}
        //========== find min posistive value in set of gamma ========//
        Mat ne = find_element_equal_less_zero(Sr); //negative element is set to be inf
        for (int h=0; h<ne.rows; h++)
        {
            Sr.at<double>(0, ne.at<double>(h,0)) = numeric_limits<double>::infinity();
        }
        double r_h;
        double maxVal;
        minMaxLoc(Sr, &r_h, &maxVal, &minLoc, &maxLoc);
        r_h = double(r_h);
        double p_h;
        p_h = minLoc.x;	//Sr is a row matrix
        p_h = cvFloor(p_h/2);
        //=========== increase coefficiennt beta(Sa) in the direction of sign of its corellation with y (corellation with y is X'*y)==========//
        sign_c_Sa = sign_element_matrix(c, Sa);
        Mat beta_increament = (r_h*sign_c_Sa).mul(Wa);
        vector<double> beta_increment_vector;
        for(int ic=0; ic<beta_increament.rows; ic++)
        {
            beta_increment_vector.push_back(beta_increament.at<double>(ic,0));
        }

        for (int h=0; h<Sa.cols; h++)
        {
            beta.at<double>(int(Sa.at<double>(0,h)) ,0) = beta.at<double>(int(Sa.at<double>(0,h)) ,0) + beta_increment_vector[h];
        }
        // =========== calculate residual yr , update vector of current correlation c and update active set Sa ======= //
        yr = y - X*beta;
        c= X_transpose * yr;
        //update active set
        transpose(Sa, Sa);
        Sa.resize(Sa.rows + 1);
        Sa.at<double>(Sa.rows-1, 0) = Sc.at<double>(0, p_h);
        transpose(Sa, Sa);
    }

    return beta;
}

int Rec_Lasso(Mat T, Mat D, double cr, double it, parameter_OMP param )
{
    int flg;		//flg is always an integer ?

    Mat T_temp;
    pow(T,2,T_temp);
    reduce(T_temp, T_temp, 0, CV_REDUCE_SUM, CV_64F);

    for (int i=0; i<T_temp.cols; i++)
    {
        T_temp.at<double>(0,i)=sqrt(T_temp.at<double>(0,i));
    }
    repeat(T_temp, T.rows, 1, T_temp);
    divide(T, T_temp, T);

    Mat D_temp;
    pow(D,2,D_temp);
    reduce(D_temp, D_temp, 0, CV_REDUCE_SUM, CV_64F);

    for (int i=0; i<D_temp.cols; i++)
    {
        D_temp.at<double>(0,i)=sqrt(D_temp.at<double>(0,i));
    }
    repeat(D_temp, D.rows, 1, D_temp);
    divide(D, D_temp, D);
    int m=D.rows;
    int n=D.cols;

    Mat be;
    int itx=T.cols;

    int i=0;
    for (i=0; i<it; i++)	//from 0?
    {
        int cp;
        cp=cvRound(m/cr);		//cr must different 1 to active the random projection matrix, if cr=1 => don't use random projection and we can set it = 0
        Mat cm(cp, m, CV_64F);
        randn(cm, 0, 1);
        Mat tec;
        tec=cm*T;
        Mat Dc(cm.rows, D.cols, CV_64F);
        Dc=cm*D;

        Mat x(Dc.cols, 1, CV_64F); //because lars_lu return matrix beta with size = n*1
        int j=0;
        Mat xx;

        for (j=0; j<itx; j++)
        {
            xx=lars_lu(tec.col(j), Dc, param.err, param.nu);

            transpose(x,x);		//for first loop, x is already defined as row vector
            transpose(xx,xx); //now, xx is a row vector
            xx.row(0).copyTo(x.row(x.rows-1)); //copy xx to last row of x
            x.resize(x.rows+1, 0);
            transpose(x,x);
            transpose(xx,xx);
        }

        transpose(x,x);
        x.resize(x.rows-1,0); //delete last row of x, because in last loop, x will have 1 row unwanted
        transpose(x,x);

        if(x.rows != be.rows)	//first loop
        {
            x.copyTo(be);
        }
        else
        {
            transpose(be,be);
            be.resize(be.rows + x.cols,0);
            transpose(be,be);
            Mat ROI_be_new=be(Rect(x.cols*i, 0, x.cols, x.rows));
            x.copyTo(ROI_be_new);
        }
    }

    /*=======================================max frequency========================================*/
    Mat mvm;
    //	double SEUIL = -1.0;
    reduce(be, mvm, 0, CV_REDUCE_MAX, CV_64F);//find mvm is maximum of each column of be
    Mat pvm_temp(be.rows, 1, CV_64F);//to store each column of be to find index of mvm
    Mat pvm(1, be.cols, CV_64F);//find pvm is indexs of each mvm in each column

    int h=0;
    for (h=0; h<mvm.cols; h++)
    {
        be.col(h).copyTo(pvm_temp);//take each column of be
        double minVal_be, maxVal_be;
        Point minLoc_be, maxLoc_be;
        minMaxLoc(pvm_temp, &minVal_be, &maxVal_be, &minLoc_be, &maxLoc_be);
        //			if (maxVal_be<SEUIL) {pvm.at<double>(0,h) = mvm.cols;}
        //			else
        {pvm.at<double>(0,h) = maxLoc_be.y;}//index of mvm is index of max element of current column
    }

    Mat up(1, be.rows+1, CV_64F);	//contains index of atom in Dictionary, begin from 0
    for (int h=0; h<up.cols; h++)
    {
        up.at<double>(0,h) = h;
    }

    Mat ph=hist(pvm,up);//ph is a row vector
    //find max value mvv of ph and index of max value pvv in ph
    double minVal_ph, mvv;
    Point minLoc_ph, maxLoc_ph;
    minMaxLoc(ph, &minVal_ph, &mvv, &minLoc_ph, &maxLoc_ph);
    int pvv = maxLoc_ph.x;
    double pv=up.at<double>(0,pvv);
    if (maxLoc_ph.x==mvm.cols) {
        pv=n;
    }

    // ===========================================================================================
    if (pv <= n-1)	// n can equal 0
    {
        flg=pv;
    } else {
        flg=999;
    }

    return flg;
}

Tar_properties Rec_two_stage_sparse(Mat b, Tar_properties Tar, Mat ScaR, Mat Sca_T, Mat Sca_R_N, parameter_OMP param, double cr, double itr, int wbh_d, int wbw_d, int wbh_n, int wbw_n, Mat sf, int nff, int coords[4])
{
    /*============This function is to detect object and then further verify it===========*/
    //		%%%%%%%%%%%%%%%% FIRST STAGE DETECT THE TARGET %%%%%%%%%%%%%%%%%%%%%%
    /*=== Calculate the new region that possibility to have an object ===*/
    Mat Reg = Region_seg(b,  Tar.pnew.col(0),  Tar.siz.col(nff-1),  ScaR);
    Mat sz;
    Mat subim;
    Mat Db_T;

    /*=================== Get Dictationary (Db_T) that contains data of sliding windows (subim) in various size ==========*/
    for (int ir=0; ir < Sca_T.cols; ir++)
    {
        //Sca_T is contains various scale to change size, in this program, it just have 1 scale
        //==== change size of selected object with scale ==========//
        sz = Sca_T.col(ir).mul(Tar.siz.col(nff-1));
        //round all elements of sz
        for(int ih=0; ih<sz.rows; ih++)
        {for (int jc=0; jc<sz.cols; jc++)
            {sz.at<double>(ih,jc)=cvRound(sz.at<double>(ih,jc));}}
        double h = sz.at<double>(1,0);
        double w = sz.at<double>(0,0);
        //==========================================================//
        Mat subim_temp=im_seg_resize(Reg,h,w,wbh_d,wbw_d,Tar.siz.col(0)); //sliding windows
        //===========creat Db_T contains [sliding windows; index; size]=========//
        Mat subim_temp1;
        repeat(sz,1,subim_temp.cols,subim_temp1);
        Mat subim(subim_temp.rows + subim_temp1.rows, subim_temp.cols, CV_64F);
        Mat ROI_subim = subim(Rect(0, 0, subim_temp.cols, subim_temp.rows));
        Mat ROI_subim1 = subim(Rect(0, subim_temp.rows, subim_temp.cols, subim_temp1.rows));
        subim_temp.copyTo(ROI_subim);
        subim_temp1.copyTo(ROI_subim1);
        //this loop just execut 1 time, so don't need to resize matrix Db_T
        subim.copyTo(Db_T);
        //=====================================================================//
    }


    //candidate objects in region for reitrival
    int md=Db_T.rows;
    int nd=Db_T.cols;
    Mat ROI_Db_T=Db_T(Rect(0, 0, Db_T.cols, Db_T.rows-4)); //-4 not -5 because this is a distance
    Mat D;
    ROI_Db_T.copyTo(D);	//exclude location and size information ,and applied in Rec_lasso
    Mat te(Tar.fea.rows, sf.cols, CV_64F);
    for (int i=0; i<sf.cols; i++) {
    	Tar.fea.col(sf.at<double>(0,i)-1).copyTo(te.col(i));
    }

    int pv=Rec_Lasso(te, D, cr, itr, param);

    //		%%%%%%%%%%%%%%%% Second stage further verify recognition results%%%%%%%%%%%%%%%%%%%%%%
    if(pv!=999)	//object detected in 1st stage
    {
        Mat t2;
        D.col(pv).copyTo(t2); //use detect result of 1st stage to be a target of 2nd stage
        Mat Db_t_pv;		 //extract size and index of target (result in 1st stage)
        Db_T.col(pv).copyTo(Db_t_pv);
        Mat pca(4,1,CV_64F);
        pca.at<double>(0,0) = Db_t_pv.at<double>(Db_t_pv.rows-4 ,0);
        pca.at<double>(1,0) = Db_t_pv.at<double>(Db_t_pv.rows-3 ,0);
        pca.at<double>(2,0) = Db_t_pv.at<double>(Db_t_pv.rows-2 ,0);
        pca.at<double>(3,0) = Db_t_pv.at<double>(Db_t_pv.rows-1 ,0);
        Mat sz2(2,1,CV_64F);
        sz2.at<double>(1,0) = pca.at<double>(pca.rows-1 ,0);
        sz2.at<double>(0,0) = pca.at<double>(pca.rows-2 ,0);
        /*============== creat new dictionary D2 to verify result in 1st stage ====================*/
        /*======D2 contains 2 parts: 1st part is Tar.fea contains target in 1st column and target+noise in rest=========*/
        /*========================== 2nd part is Tar.feaN contains background after hide target ========================*/
        /*====== so,if the result of verifying process is in the 1st part of dictionary => object verified ========*/
        /*=== else,if the result of verifying process is in the 2nd part of dictionary => object is a part of background ======*/
        Mat D2(Tar.fea.rows, Tar.fea.cols + Tar.feaN.cols, CV_64F);
        Mat ROI_D2 = D2(Rect(0, 0, Tar.fea.cols, Tar.fea.rows));
        Tar.fea.copyTo(ROI_D2);
        Mat ROI_D2_1 = D2(Rect(Tar.fea.cols, 0, D2.cols - Tar.fea.cols, Tar.fea.rows));
        Tar.feaN.copyTo(ROI_D2_1);

        int pv2 = Rec_Lasso(t2, D2, cr, itr, param); //run detect in 2nd stage

        if((pv2>=0) & (pv2<=(Tar.fea.cols-1)))
        {
            //********* target is verified in the 1st part of dictionary => detection result of 1st stage is correct **************
            Db_T.col(pv).copyTo(Db_t_pv);
            Mat pij(2,1,CV_64F);	//take index of windows in column pv (result detection of first stage)
            pij.at<double>(0,0) = Db_t_pv.at<double>(Db_t_pv.rows-4, 0);
            pij.at<double>(1,0) = Db_t_pv.at<double>(Db_t_pv.rows-3, 0);
            Mat sz2(2,1,CV_64F);
            sz2.at<double>(0,0) = Db_t_pv.at<double>(Db_t_pv.rows-2, 0);
            sz2.at<double>(1,0) = Db_t_pv.at<double>(Db_t_pv.rows-1, 0);
            Mat pt;
            p_reg.copyTo(pt);	//p_reg is a global variable ?
            //take windows pv in frame b. top_left point is in pt and index of windows in pij
            double top_left_col = pt.at<double>(0,0) + (pij.at<double>(1,0))*wbw_d;
            double top_left_row = pt.at<double>(1,0) + (pij.at<double>(0,0))*wbh_d;
            double size_col = sz2.at<double>(0,0);
            double size_row = sz2.at<double>(1,0);

            Mat ROI_b = b(Rect(top_left_col, top_left_row, size_col, size_row));
            Mat aaa;
            ROI_b.copyTo(aaa);
            Mat ppp(4,1,CV_64F);
            ppp.at<double>(0,0) = top_left_col + 1;//why + 1?
            ppp.at<double>(1,0) = top_left_row + 1;
            ppp.at<double>(2,0) = sz2.at<double>(0,0);
            ppp.at<double>(3,0) = sz2.at<double>(1,0);

            //shift
            Mat ROI_shift_Tar_pos = Tar.pos(Rect(nff-1, 0, Tar.pos.cols-nff+1, Tar.pos.rows));
            shiftCols(ROI_shift_Tar_pos, 1);
            ROI_shift_Tar_pos.copyTo(Tar.pos(Rect(Tar.pos.cols-ROI_shift_Tar_pos.cols, Tar.pos.rows-ROI_shift_Tar_pos.rows, ROI_shift_Tar_pos.cols, ROI_shift_Tar_pos.rows)));
            //Update Tar.pos - new positon update
            Tar.pos.at<double>(0,nff-1) = ppp.at<double>(0,0);
            Tar.pos.at<double>(1,nff-1) = ppp.at<double>(1,0);
            //Update Tar.pnew - unreliable position
            transpose(Tar.pnew,Tar.pnew);
            Tar.pnew.resize(Tar.pnew.rows+1);
            transpose(Tar.pnew, Tar.pnew);
            shiftCols(Tar.pnew, 1);
            Tar.pnew.at<double>(0,0)=ppp.at<double>(0,0);
            Tar.pnew.at<double>(1,0)=ppp.at<double>(1,0);
            //Update Tar.siz - new size update
            Mat ROI_shift_Tar_siz = Tar.siz(Rect(nff-1, 0, Tar.siz.cols-nff+1, Tar.siz.rows));
            shiftCols(ROI_shift_Tar_siz, 1);
            ROI_shift_Tar_siz.copyTo(Tar.siz(Rect(Tar.siz.cols-ROI_shift_Tar_siz.cols, Tar.siz.rows-ROI_shift_Tar_siz.rows, ROI_shift_Tar_siz.cols, ROI_shift_Tar_siz.rows)));
            Tar.siz.at<double>(0,nff-1) = ppp.at<double>(2,0);
            Tar.siz.at<double>(1,nff-1) = ppp.at<double>(3,0);
            //Update Tar.fea - first part of dictionary D2 update = [object  object+Noise]
            Mat bbb;
            D.col(pv).copyTo(bbb);
            Mat ROI_shift_Tar_fea = Tar.fea(Rect(nff-1, 0, Tar.fea.cols-nff+1, Tar.fea.rows));
            shiftCols(ROI_shift_Tar_fea, 10);
            ROI_shift_Tar_fea.copyTo(Tar.fea(Rect(nff-1, 0, Tar.fea.cols-nff+1, Tar.fea.rows)));
            ROI_shift_Tar_fea = Tar.fea(Rect(nff-1, 0, 10, Tar.fea.rows));
            Mat Tar_fea_temp(Tar.fea.rows, 10, CV_64F);
            bbb.col(0).copyTo(Tar_fea_temp.col(0));
            repeat(bbb, 1, 9, bbb);
            Mat Gauss(bbb.rows, 9, CV_64F);
            randn(Gauss, 0, 1); //mean=0 and stdvv=1 ?
            bbb = Gauss + bbb;
            Mat ROI_Tar_fea_temp = Tar_fea_temp(Rect(1, 0, 9, Tar_fea_temp.rows));
            bbb.copyTo(ROI_Tar_fea_temp);

            Tar.flag = 0; //successful label

            //Update Tar.feaN - 2nd part of dictionary D2 update = background
            if (1)
            {
                Mat VV = Region_Negative(b, wbh_n, wbw_n, Tar.pos, Tar.siz, Sca_R_N, nff);	//ATTENTION: correct nff index in Region_negative
                if (Tar.feaN.cols < 400)
                {
                    transpose(Tar.feaN, Tar.feaN);
                    Tar.feaN.resize(Tar.feaN.rows + VV.cols ,0);
                    transpose(Tar.feaN, Tar.feaN);
                    Mat ROI_Tar_feaN_VV = Tar.feaN(Rect(Tar.feaN.cols - VV.cols, 0, VV.cols, VV.rows));
                    VV.copyTo(ROI_Tar_feaN_VV);
                }
                else
                {
                    shiftCols(Tar.feaN, -VV.cols);
                    Mat ROI_Tar_feaN_VV = Tar.feaN(Rect(Tar.feaN.cols - VV.cols, 0, VV.cols, VV.rows));
                    VV.copyTo(ROI_Tar_feaN_VV);
                }
            }

			coords[0] = ppp.at<double>(0,0);
			coords[1] = ppp.at<double>(1,0);
			coords[2] = ppp.at<double>(0,0) + ppp.at<double>(2,0) - 1;
			coords[3] = ppp.at<double>(1,0) + ppp.at<double>(3,0) - 1;
		    // p.at<double>(0,0) = ppp.at<double>(0,0);
		    // p.at<double>(1,0) = ppp.at<double>(1,0);
		    // sz.at<double>(0,0) = ppp.at<double>(2,0);
		    // sz.at<double>(1,0) = ppp.at<double>(3,0);

            //add ppp to Tar_posres
            transpose(Tar.posres, Tar.posres);
            Tar.posres.resize(Tar.posres.rows+1, 0);
            transpose(Tar.posres,Tar.posres);
            ppp.col(0).copyTo(Tar.posres.col(Tar.posres.cols-1));
        }
        else //*************** target is verified in the 2nd part of dictionary => detection result of 1st stage is incorrect **************
        {
            Tar.flag = Tar.flag +1;
        } //enlarge region
    }
    else //*************** target is not detected in 1st stage **************
    {
        Tar.flag = Tar.flag +1;
    }//enlarge region

    return Tar;
}

Mat getMatFromRGB(u8 *red, u8 *green, u8 *blue, int imgSize[2])
{
	int cols= 0, rows=0, indRGB=0;
    Mat rgbMat;

	Mat R = Mat(imgSize[1], imgSize[0], CV_64F, Scalar::all(0));
    Mat G = Mat(imgSize[1], imgSize[0], CV_64F, Scalar::all(0));
    Mat B = Mat(imgSize[1], imgSize[0], CV_64F, Scalar::all(0));
	for (rows=0; rows<imgSize[1]; rows++) {
		for (cols=0; cols<imgSize[0]; cols++) {
			R.at<double>(rows, cols) = red[indRGB];
			G.at<double>(rows, cols) = green[indRGB];
			B.at<double>(rows, cols) = blue[indRGB];
		 	// printf("ASN %d : %d - %d - %d\n", indRGB, red[indRGB], green[indRGB], blue[indRGB]);
		 	// printf("ASN %d : %lf - %lf - %lf\n", indRGB, R.at<double>(0, indRGB), G.at<double>(0, indRGB), B.at<double>(0, indRGB));
		 	indRGB++;
		}
	}
	vector<Mat> vectRGB;
    vectRGB.push_back(R);
    vectRGB.push_back(G);
    vectRGB.push_back(B);
    merge(vectRGB,rgbMat);

    return rgbMat;
}


extern "C" {

void initTracking(u8 *red, u8 *green, u8 *blue, int imgSize[2], int coords[4])
{
	printf("Trackimg : initTracking\n");

    int i,j=0;

    /*=========================== PARAMETERS ============================*/

    //===========random permutation========//
    Mat sss (1,100,CV_64F);
    randu(sss,1,100);
    //===========samples in Tar used in Lasso for recognition=======//
    // Mat sf (1,22,CV_64F);
    sf.at<double>(0,0)=1;
    for (int i2=0; i2<10; i2++)
    {sss.col(i2).copyTo(sf.col(i2+1));}
    for (int i2=0; i2<11; i2++)
    {sf.at<double>(0,11+i2)=(nff+2*i2);}
    //=========== Scale use for creaf ROI ==============//
    // Mat Sca_T (2,1,CV_64F);
    Sca_T.at<double>(0,0)=1;
    Sca_T.at<double>(1,0)=1;

    // Mat Sca_R (2,1,CV_64F); //scales of region for retrieval
    Sca_R.at<double>(0,0)=2;
    Sca_R.at<double>(1,0)=2;

    // Mat Sca_R_O (2,1,CV_64F);//scales of region for retrieval when occlusion is detected
    Sca_R_O.at<double>(0,0)=3;
    Sca_R_O.at<double>(1,0)=3;

    // Mat Sca_R_N (2,1,CV_64F);
    Sca_R_N.at<double>(0,0)=3;
    Sca_R_N.at<double>(1,0)=3;
    // ========== Step of sliding windows ========//
    wbw.d=4;
    wbh.d=4;

    wbw.n=10;
    wbh.n=10;

    // int wbw_d=4; //for object segment in ROI (for animal: wbw_d=4 ; wbw_n=10)
    // int wbh_d=4;
    // int wbw_n=10; //for background sample
    // int wbh_n=10;
    // // ========== another parameters =============//
    // int vg=1;   //scale Gaussian noise for initial samples
    // double cr=30; //Gaussian compression rate in lasso recognition (for animal: 30 - 3/4)
    // double itr=3;  //iterative times for random compression in lasso recognition

    // ========== error for OMP ============//
    param.err=0.001;
    param.nu=20;

    /*===============================================================================================================*/

    //=======================read first image=========================//
    Mat a = getMatFromRGB(red, green, blue, imgSize);

    //======================get selected object==========================//

    p.at<double>(0,0) = coords[0];
    p.at<double>(1,0) = coords[1];
    sz.at<double>(0,0) = coords[2] - coords[0] + 1;
    sz.at<double>(1,0) = coords[3] - coords[1] + 1;
   
    Mat aa(sz.at<double>(0,0),sz.at<double>(1,0), CV_64FC3, Scalar::all(0)); //selected object
    aa = a(Rect(p.at<double>(0,0), p.at<double>(1,0), sz.at<double>(0,0), sz.at<double>(1,0)));

    //===================== initialize TAR set =========================//

    /*================= Create Tar.fea ========================
        ================ Tar.fea is a matrix contains: [Tar.fea [Tar.fea + Gausse]]=============
        ================ with Tar.fea is a selected object =====================================*/

    Mat Tar_fea1= aa.clone().reshape ( 1, 1 );	//Tar.fea contains selected object in 1 column
    Mat Tar_fea11;
    transpose(Tar_fea1,Tar_fea11);

    Mat Gau_T(Size(nf-1,Tar_fea11.rows),CV_64F); //Gaussien T
    randn(Gau_T,0,vg);

    Mat Tar_fea111;
    repeat(Tar_fea11,1,nf-1,Tar_fea111); //Tar_fea111 la lap lai 199 lan Tar_fea11 (aa)
    Tar_fea111=Gau_T+Tar_fea111;
    Mat Tar_fea(Size(nf,Tar_fea11.rows),CV_64F);
    Tar_fea11.copyTo(Tar_fea.col(0));
    for (int i2=0; i2<Tar_fea111.cols; i2++)
    {Tar_fea111.col(i2).copyTo(Tar_fea.col(i2+1));}
    Tar_fea.copyTo(Tar.fea);
    /*================= Create Tar.pos ========================
        ================ Tar.pos is a matrix contains: [p p p p ... p]=============
        ================ with p is top-left position vector ============================*/

    Mat Tar_pos(Size(nf,p.rows),CV_64F);
    for(int i2=0; i2<Tar_pos.cols; i2++)
    {p.col(0).copyTo(Tar_pos.col(i2));} //p is a global var
    Tar_pos.copyTo(Tar.pos);
    /*================= Create Tar.siz ========================
            ================ Tar.pos is a matrix contains: [sz sz sz.... sz]=============
            ================ with sz is size of selected object vector ===================*/

    Mat Tar_siz(Size(nf,sz.rows),CV_64F);
    for(int i2=0; i2<Tar_siz.cols; i2++)
    {
        sz.col(0).copyTo(Tar_siz.col(i2));
    }
    Tar_siz.copyTo(Tar.siz);
    /*================= Create Tar.flag ========================
            ============ is a flag marks successful regcognition or not =============*/
    int Tar_flag=0;
    Tar.flag = Tar_flag;
    /*================= Create Tar.posres ========================*/
    Mat Tar_posres(Size(p.cols,p.rows+sz.rows),CV_64F);
    Tar_posres.at<double>(0,0)=p.at<double>(0,0);
    Tar_posres.at<double>(1,0)=p.at<double>(1,0);
    Tar_posres.at<double>(2,0)=sz.at<double>(0,0);
    Tar_posres.at<double>(3,0)=sz.at<double>(1,0);
    Tar_posres.copyTo(Tar.posres);
    /*================= Create Tar.pnew ========================*/
    //assume that object moves regularly
    Mat Tar_pnew(2,2,CV_64F);
    Tar_pnew.col(0)=(Tar_pos.col(nff-1)+Tar_pos.col(nff-1)-Tar_pos.col(nff-2));
    Tar_pos.col(nff-1).copyTo(Tar_pnew.col(1));
    Tar_pnew.copyTo(Tar.pnew);

    /*================= Create Tar.feaN ========================*/
    Mat Tar_feaN;
    Tar_feaN=Region_Negative(a, wbh_n, wbw_n, Tar_pos, Tar_siz, Sca_R, nff);
    Tar_feaN.copyTo(Tar.feaN);
    /*================================================== READ FRAME, TRACKING AND VALIDATION =============================================================*/
}


void startTracking(u8 *red, u8 *green, u8 *blue, int imgSize[2], int coords[4])
{
    //!TODO : ASN => fix it !!!
	// printf("Trackimg : startTracking in image nb %d\n", iImgIndex);

    //================read next image========================
    Mat b = getMatFromRGB(red, green, blue, imgSize);

    //======================== detect succesfull ======================
    if (Tar.flag == 0)
    {
		printf("Trackimg : Object found\n");
        //before run 2nd frame, Tar_flag = 0 bcz initialize in 1st frame = 0
        Mat ScaR;
        Sca_R.copyTo(ScaR);
        Tar = Rec_two_stage_sparse(b, Tar, ScaR, Sca_T, Sca_R_N, param, cr, itr, wbh_d, wbw_d, wbh_n, wbw_n, sf, nff, coords);
    }

    //========================= detect failed =========================
    if (Tar.flag != 0)
    {
        Mat balance (Tar.pnew.rows, 1, CV_64F);
        printf("Trackimg : Object lost\n");
        //============= enlarge region for detection =================
        if (Tar.flag > 1)
        {
        	printf("Trackimg : Object lost : try to detect in bigger region\n");
            //======= try to detect in bigger region =======
            Mat ScaR;
            Sca_R_O.copyTo(ScaR);
            Tar = Rec_two_stage_sparse(b, Tar, ScaR, Sca_T, Sca_R_N, param, cr, itr, wbh_d, wbw_d, wbh_n, wbw_n, sf, nff, coords);
        }
        // =========== after detect in enlarge region ===============
        if (Tar.flag != 0)
        {
        	printf("Trackimg : Object lost : after detect in enlarge region\n");
            //************ Tar_flag = 1
            //=========display image b========//
            vector<Mat> RGB3;
            split(b,RGB3);
            Mat R3 = RGB3[0];
            Mat G3 = RGB3[1];
            Mat B3 = RGB3[2];
            //MERGE
            vector<Mat> BGR3;
            BGR3.push_back(B3);
            BGR3.push_back(G3);
            BGR3.push_back(R3);
            Mat c3;
            merge(BGR3,c3);
            c3.convertTo(c3,CV_8UC3);
            // imshow("animal", c3);
            // waitKey(2);
            //====================================//
            if (Tar.pnew.cols < 10)
            {//============fill balance with 0=========
                for(int ih=0; ih<balance.rows; ih++)
                {
                    balance.at<double>(ih,0) = 0;
                }
            }
            else
            {//=============calculate balance===========
                Mat ROI_Tar_pnew_mean_1 = Tar.pnew(Rect(1, 0, 8, Tar.pnew.rows));
                Mat temp1; ROI_Tar_pnew_mean_1.copyTo(temp1);
                Mat ROI_Tar_pnew_mean_2 = Tar.pnew(Rect(2, 0, 8, Tar.pnew.rows));
                Mat temp2; ROI_Tar_pnew_mean_2.copyTo(temp2);
                Mat sum = temp1 - temp2;
                reduce(sum, balance, 1, CV_REDUCE_AVG);
            }
            //calculate Tar.pnew base on balance and update Tar.pnew
            Mat Tar_pnew_temp(Tar.pnew.rows, 1, CV_64F);
            Mat temp1;Tar.pnew.col(0).copyTo(temp1);
            Mat temp2;Tar.pnew.col(1).copyTo(temp2);
            Tar_pnew_temp = temp1 - temp2;
            for(int ii=0; ii<Tar_pnew_temp.rows; ii++){
            	Tar_pnew_temp.at<double>(ii,0)=cvRound(Tar_pnew_temp.at<double>(ii,0));
            }
            Tar_pnew_temp = Tar_pnew_temp*0.3 + Tar.pnew.col(0) + 0.3*balance;
            //update Tar.pnew
            transpose(Tar.pnew, Tar.pnew);
            Tar.pnew.resize(Tar.pnew.rows + Tar_pnew_temp.cols, 0);
            transpose(Tar.pnew, Tar.pnew);
            Tar_pnew_temp.col(0).copyTo(Tar.pnew.col(Tar.pnew.cols-1));

            //draw a rectangle
			coords[0] = Tar.pnew.at<double>(0,0);
			coords[1] = Tar.pnew.at<double>(1,0);
			coords[2] = Tar.pnew.at<double>(0,0)+Tar.siz.at<double>(0,nff-1) - 1;
			coords[3] = Tar.pnew.at<double>(1,0)+Tar.siz.at<double>(1,nff-1) - 1;
            // rectangle(c3, Point(Tar.pnew.at<double>(0,0), Tar.pnew.at<double>(1,0)), Point(Tar.pnew.at<double>(0,0)+Tar.siz.at<double>(0,nff-1),  Tar.pnew.at<double>(1,0)+Tar.siz.at<double>(1,nff-1)),Scalar(0,0, 255), 2);
            // imshow("animal", c3);

            Tar.flag = Tar.flag + 1;
            Mat Tar_posres_temp (Tar.pnew.rows + Tar.siz.rows, 1, CV_64F);
            Mat ROI_Tar_posres_temp = Tar_posres_temp(Rect(0, 0, 1, Tar.pnew.rows));
            Tar.pnew.col(0).copyTo(ROI_Tar_posres_temp);
            ROI_Tar_posres_temp = Tar_posres_temp(Rect(0, Tar.pnew.rows, 1, Tar.siz.rows));
            Tar.siz.col(nff-1).copyTo(ROI_Tar_posres_temp);
            transpose(Tar.posres, Tar.posres);
            Tar.posres.resize(Tar.posres.rows + 1, 0);
            transpose(Tar.posres, Tar.posres);
            Tar_posres_temp.col(0).copyTo(Tar.posres.col(Tar.posres.cols-1));
        }
    }
}


} //extern "C"